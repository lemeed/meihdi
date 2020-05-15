#!/bin/bash

install package
sudo zypper install -f -y apache2
sudo zypper install -f -y -n proftpd
sudo zypper install -f -y mariadb
sudo systemctl start mysql
sudo zypper install -f -y phpMyAdmin
sudo zypper install -f -y jq
start web server
systemctl start apache2



#internal
nomberUser=$(sudo jq '.ListUser |length' user.json)
let "nomberUser-=1"


number_account=$1
htmlpage=$'<!DOCTYPE html>\r<html>\r<head>\r\t<meta charset="utf-8">\r</head>\r<body>\r<h1>Hello World</h1>\r</body>\n</html>\r'


sudo cp -r ./default-server.conf /etc/apache2/
sudo mkdir /srv/www/htdocs/phpmyadmin
sudo cp -r /usr/share/phpMyAdmin/* /srv/www/htdocs/phpmyadmin

create_vhost() {
	local vhost="<VirtualHost *:80>\nDocumentRoot /home/$1\nServerName $1.localhost\n<Directory /home/$1>\nOptions All\nAllowOverride All\nRequire all granted\n</Directory>\n</VirtualHost>"
	sudo touch /etc/apache2/vhosts.d/$1.conf
	sudo echo -e "127.0.0.1 $1.localhost" >>/etc/hosts
	sudo echo -e $vhost >/etc/apache2/vhosts.d/$1.conf
	sudo systemctl reload apache2

}

create_account() {
	local res="^[0-9]{1,2}$"

	if ! [[ $number_account =~ $res ]]; then
		echo "Not a integer"
	else
		for i in $(seq 1 $number_account); do
			sudo useradd "account"$i
			sudo mkdir /home/"account${i}"
			sudo chmod 755 /home/"account${i}"
			sudo touch /home/"account${i}"/index.html
			sudo chmod 755 /home/"account${i}"/index.html
			sudo echo $htmlpage >/home/"account${i}"/index.html
			sudo chown -R "account${i}" /home/"account${i}"
			create_vhost "account${i}"
			create_db "$1"
		done
	fi

}

create_db() {

	local newUser="$1"
	if [ -z "$2" ] && [ -z "$3" ]; then
		newDbPassword='admin'
		newDb="$1Db"
	else
		echo {$3}
		newDbPassword="$3"
		newDb="$2"
	fi
	host="localhost"
	commands="CREATE DATABASE \`${newDb}\`;CREATE USER '${newUser}'@'${host}' IDENTIFIED BY '${newDbPassword}';GRANT USAGE ON *.* TO '${newUser}'@'${host}' IDENTIFIED BY '${newDbPassword}';GRANT ALL privileges ON \`${newDb}\`.*
			TO '${newUser}'@'${host}';FLUSH PRIVILEGES;"

	echo "${commands}" | /usr/bin/mysql -u root
}

createSiteWithJson() {
	sudo mkdir /home/"$1"
	sudo chmod 755 /home/"$1"
	sudo cp -r $2/* /home/"$1"/
	sudo chmod 755 /home/"$1"/index.html
	sudo chown -R "$1" /home/"$1"
}

createAccountJson() {
	for i in $(seq 0 $nomberUser); do

		name=$(sudo jq '.ListUser['${i}'].user[0].name' user.json)
		nameUser="${name:1:${#name}-2}"
		nameDb=$(sudo jq '.ListUser['${i}'].user[0].DataBaseName' user.json)
		nameDbWithout="${nameDb:1:${#nameDb}-2}"
		passwordDb=$(sudo jq '.ListUser['${i}'].user[0].DataBasePassword' user.json)
		passwordDbWithout="${passwordDb:1:${#passwordDb}-2}"
		src=$(sudo jq '.ListUser['${i}'].user[0].SiteDirectory' user.json)
		srcDirectory="${src:1:${#src}-2}"
		sudo useradd ${nameUser}
		createSiteWithJson ${nameUser} ${srcDirectory}

		create_vhost ${nameUser}

		create_db ${nameUser} ${nameDbWithout} ${passwordDbWithout}
	done

}

if [ $# -eq 0 ]; then
	createAccountJson
else
	create_account
fi

/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 */

#include "rainbow_table.hpp"
#include <cmath>
#include <fstream>

/**
 * @see ./rainbow_table.hpp
 */
void rainbow_table::generate(std::string target_file_path, unsigned long long max_file_size, std::ios::openmode open_mode) {
    unsigned long long curr_file_size = 0;
    
    std::ofstream output_file;
    output_file.open(target_file_path, open_mode);
    
    std::string start_password;
    while (curr_file_size < max_file_size) {
        start_password = _generator();
        std::string final_password = deep_reduce(start_password);
        std::string output = start_password + " " + final_password;
        curr_file_size += output.size(); // augment local file size counter
        
        output_file << output << std::endl;
    }
    
    output_file.close();
}

/**
 * @see ./rainbow_table.hpp
 */
std::string rainbow_table::deep_reduce(std::string start_password) {
    bool is_debug = std::getenv("RT_DEBUG") != NULL;
    
    std::string password = start_password;
    unsigned init_pwd_len = static_cast<unsigned>(start_password.length());
    
    if (is_debug) {
        std::cout << password << ": ";
    }
    
    for (unsigned i = 0; i < _reduction_count; i++) {
        std::string password_hash = _hasher(password);
        password = rainbow_table::reduce(i + 1, password_hash, init_pwd_len);
        
        if (is_debug) {
            std::cout << password;
            if (i < _reduction_count - 1) {
                std::cout << " ";
            }
        }
    }
    
    if (is_debug) {
        std::cout << ";" << std::endl << std::endl;
    }
    
    return password;
}

/**
 * @see ./rainbow_table.hpp
 */
std::string rainbow_table::reduce(unsigned reduce_index, std::string input_hash, unsigned result_len) {
    return input_hash.substr(0, result_len);
}

/**
 * @see ./rainbow_table.hpp
 */
std::unique_ptr<std::string> rainbow_table::get_match_from_chain(std::string start_password, std::string lookup_hash) {
    std::string password = start_password;
    unsigned init_pwd_len = static_cast<unsigned>(start_password.length());
    
    for (unsigned i = 0; i < _reduction_count; i++) {
        std::string password_hash = _hasher(password);
        if (lookup_hash == password_hash) {
            return std::make_unique<std::string>(password);
        }
        password = rainbow_table::reduce(i + 1, password_hash, init_pwd_len);
    }
    
    return nullptr;
};

/**
 * @see ./rainbow_table.hpp
 */
std::unique_ptr<std::string> rainbow_table::find(std::string src_file_path, std::string input_hash) {
    
    std::ifstream rt_src_file(src_file_path);
    
    std::unique_ptr<std::string> decrypted_pwd = nullptr;
    
    for (unsigned password_size = _min_password_size; password_size <= _max_password_size; password_size++) {
        unsigned reduc_reverse_cpt = 0;
        while (reduc_reverse_cpt < _reduction_count && decrypted_pwd == nullptr) {
            // reset reading pos to beginning
            rt_src_file.clear();
            rt_src_file.seekg(0, std::ios::beg);
            
            std::string password;
            std::string hash = input_hash;
            for (unsigned i = reduc_reverse_cpt; i > 0; i--) {
                // process reductions
                password = rainbow_table::reduce(_reduction_count - reduc_reverse_cpt, hash, password_size);
                hash = _hasher(password);
            }
            
            std::string frt_start_password;
            std::string frt_end_password;
            while (rt_src_file >> frt_start_password >> frt_end_password) {
                if (frt_end_password == password) {
                    // found in line starting with password `frt_start_password`
                    std::unique_ptr<std::string> potential_password = get_match_from_chain(frt_start_password, input_hash);
                    
                    if (potential_password != nullptr) {
                        decrypted_pwd = std::move(potential_password);
                    }
                }
            }
            reduc_reverse_cpt++;
        }
    }
    return decrypted_pwd;
}

/**
 * @see ./rainbow_table.hpp
 */
void rainbow_table::find_multiple_from_file(std::string src_rt_file_path, std::string src_hash_pairs_file_path) {
    std::ifstream hash_pairs_src_file(src_hash_pairs_file_path);
    
    std::string login;
    std::string password_hash;
    
    while (hash_pairs_src_file >> login >> password_hash) {
        std::unique_ptr<std::string> potential_result = find(src_rt_file_path, password_hash);
        if (potential_result == nullptr) {
            std::cout << password_hash << " = NOT FOUND" << std::endl;
        } else {
            std::cout << password_hash << " = " << *potential_result << std::endl;
        }
    }
}

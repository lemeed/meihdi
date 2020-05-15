# -*- coding: utf-8 -*-
{
    'name': "agendaesi",

    'summary': """
        Handle calendars for ESI""",

    'description': """
        Handle calendars for ESI. Each calendar can have attendees. There are 3 types of calender : students, teachers &  administrative
    """,

    'author': "49762 & 49262",
    'website': "https://esi-bru.be",

    # Categories can be used to filter modules in modules listing
    # Check https://github.com/odoo/odoo/blob/12.0/odoo/addons/base/data/ir_module_category_data.xml
    # for the full list
    'category': 'Uncategorized',
    'version': '0.1',

    # any module necessary for this one to work correctly
    'depends': ['base'],

    # always loaded
    'data': [
		'security/security.xml',
		'security/ir.model.access.csv',
		'views/agendaesi.xml',
		'views/base.xml',
		'views/student.xml',
		'views/administrative.xml',
		'views/pedagogic.xml',
	],
    # only loaded in demonstration mode
    'demo': [
        'demo/demo.xml',
    ],
}
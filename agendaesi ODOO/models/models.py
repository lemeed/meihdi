# -*- coding: utf-8 -*-

from odoo import models, fields, api

from .Event import Event
from .Calendar import Calendar

from .StudentCalendar import StudentCalendar
from .StudentEvent import StudentEvent

from .PedagogicCalendar import PedagogicCalendar
from .PedagogicEvent import PedagogicEvent

from .AdministrativeCalendar import AdministrativeCalendar
from .AdministrativeEvent import AdministrativeEvent

__all__ = [
	'Event', 'Calendar', 
	'StudentEvent', 'StudentCalendar', 
	'PedagogicEvent', 'PedagogicCalendar', 
	'AdministrativeEvent', 'AdministrativeCalendar'
]
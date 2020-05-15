from odoo import tests
from odoo.tests import common
from datetime import date
from ..models import models
from odoo.exceptions import ValidationError

class TestEvent(common.TransactionCase):
	
    def test_owner(self):
        manager, someCalendar = self.prepare()
        eventOrga = self.env['agendaesi.studentevent'].create({
            'name':'some test event',
            'start_date':'2021-10-01',
            'end_date':'2021-10-01',
            'manager_id':manager.id,
            'calendar_id':someCalendar.id})
            
        self.assertEqual(manager.id, eventOrga.manager_id.id)
        self.assertEqual(eventOrga.is_manager, True)
		
	def prepare(self):
        manager = self.env.user
        someCalendar = self.env['agendaesi.studentcalendar'].create({'title':'Test','manager_id': manager.id})
        return manager, someCalendar

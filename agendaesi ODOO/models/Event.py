from odoo import models, fields, api, exceptions
from datetime import datetime, timedelta
import copy
import logging
_logger = logging.getLogger(__name__)


PERIODICITY_SELECTION = [
    ('daily', 'Quotidien'),
    ('weekly', 'Hebdomadaire'),
    ('monthly', 'Mensuel')
]


class Event(models.Model):
	_name = "agendaesi.event"
	
	title = fields.Char(required=True, string="The name of the event")
	
	calendar_id = fields.Many2one("agendaesi.calendar", ondelete="cascade", string="Calendar", required=True)
	
	# Duration
	start_date = fields.Datetime(required=True, string="The start date of the event", default=datetime.now().replace(microsecond=0,second=0,minute=0)+timedelta(hours=1))
	end_date = fields.Datetime(required=True, string="The end date of the event", default=datetime.now().replace(microsecond=0,second=0,minute=30)+timedelta(hours=1))
	
	duration = fields.Integer(required=False, string="Duration in minutes")
	
	is_periodic = fields.Boolean(string="Is periodic", default=False)
	
	periodicity = fields.Selection(PERIODICITY_SELECTION, required=False, default=None)
	end_periodicity = fields.Datetime(required=False, string="The end date of periodicity", default=datetime.now().replace(microsecond=0,second=0,minute=0)+timedelta(days=7))
	
	place = fields.Text(required=True, string="Place where the event will be done")
	
	manager_id = fields.Many2one("res.users", ondelete="set null", string="Manager of the event", index=True, default=lambda self: self.env.uid, readonly=True) # default set to current user
	
	max_attendee_number = fields.Integer(string="Max number of attendee", default=-1)
	
	@api.model
	def _getAllowedGroupsId(self):
		return [('groups_id', 'in', map(lambda xmlId: self.env.ref(xmlId), self._allowed_groups))]
	
	@api.constrains('attendee_ids')
	def _check_attendees_max(self):
		if (self.max_attendee_number != -1 and self.attendees_count > self.max_attendee_number):
			raise exceptions.ValidationError("Max attendee number exceeded")
	
	
	is_manager = fields.Boolean(string="is manager of the event", compute='_compute_is_manager', default=True)
	
	@api.depends('is_manager')
	def _compute_is_manager(self):
		for record in self:
			record.is_manager = self.env.user.id == record.manager_id.id
			
	@api.onchange('duration')
	def on_change_duration(self):
		for record in self:
			record.end_date = fields.Datetime.from_string(record.start_date) + timedelta(minutes=record.duration)
			
	
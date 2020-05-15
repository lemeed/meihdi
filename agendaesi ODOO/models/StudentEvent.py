from odoo import models, fields, api
import copy
import logging
from datetime import datetime, timedelta
_logger = logging.getLogger(__name__)


class StudentEvent(models.Model):
	_name = "agendaesi.studentevent"
	_inherit = 'agendaesi.event'
	
	@api.model
	def _getAllowedAttendeesMatchToEdit(self):
		return [
				('groups_id', 'in', [
					self.env.ref('agendaesi.agendaesi_student').id,
					self.env.ref('agendaesi.agendaesi_root').id
				])]
		if self.is_manager: # can edit attendees, show everyone registered to the calendar
			return [
				('groups_id', 'in', [
					self.env.ref('agendaesi.agendaesi_student').id,
					self.env.ref('agendaesi.agendaesi_root').id
				])]
		elif self.can_subscribe: # can subscribe to this event, show only myself
			return [('id', '=', self._uid)]
		else:
			return [('id', '=', -1)] # i.e. no one
	
	calendar_id = fields.Many2one("agendaesi.studentcalendar", ondelete="cascade", string="Calendar", required=True)
	
	attendee_ids = fields.Many2many("res.users", string="Attendees", domain=_getAllowedAttendeesMatchToEdit)
	
	# needed for graph view
	attendees_count = fields.Integer(string="Attendees count", compute='_get_attendees_count', store=True)
	
	@api.depends('attendee_ids')
	def _get_attendees_count(self):
		for r in self:
			r.attendees_count = len(r.attendee_ids)
			
	attendees_percentage = fields.Float(string="Attendees percentage", compute="_attendees_percentage")
	
	@api.depends('max_attendee_number', 'attendee_ids')
	def _attendees_percentage(self):
		for r in self:
			if r.max_attendee_number == -1:
				r.attendees_percentage = 0.0
			else:
				r.attendees_percentage = 100.0 * len(r.attendee_ids) / r.max_attendee_number
			
	can_subscribe = fields.Boolean(string="can subscribe to this event", compute='_compute_can_subscribe', store=False)
	
	@api.depends('can_subscribe')
	def _compute_can_subscribe(self):
		for record in self:
			can_subscribe = self.env.user.has_group('agendaesi.agendaesi_student') or self.env.user.has_group('agendaesi.agendaesi_root')
			
			if can_subscribe:
				can_subscribe = self.env.user.id not in record.attendee_ids.ids
			
			record.can_subscribe = not can_subscribe
			
			
	@api.one
	def register(self):
		if self.env.user.id not in self.attendee_ids.ids:
			self.sudo().write({ 'attendee_ids': [(4,self.env.user.id)] })
		else:
			raise ValidationError("You are already a attendee in the event")

	@api.one
	def unsubscribe(self):
		self.sudo().write({ 'attendee_ids': [(3,self.env.user.id)] })
		
	@api.model
	def create(self, values):
		if values['is_periodic'] and values['periodicity'] is not None and values['end_periodicity'] is not None:
			days_treshold=1
			if values['periodicity'] == 'daily':
				days_treshold = 1
			elif values['periodicity'] == 'weekly':
				days_treshold = 7
			elif values['periodicity'] == 'monthly':
				days_treshold = 30
			
			cp_values = copy.deepcopy(values)
			cp_values['is_periodic'] = False # prevent infinite loop
			cp_values['periodicity'] = None # prevent infinite loop
			cp_values['end_periodicity'] = None # prevent infinite loop
			
			curdate = fields.Datetime.from_string(values['start_date'])
			end = fields.Datetime.from_string(values['end_date'])
			
			while curdate < fields.Datetime.from_string(values['end_periodicity']):
				
				curdate = curdate+timedelta(days=days_treshold)
				end = end+timedelta(days=days_treshold)
				
				cp_values['start_date'] = curdate
				cp_values['end_date'] = end
				
				super(StudentEvent, self).create(cp_values)
			
			
		ev = super(StudentEvent, self).create(values)
		return ev

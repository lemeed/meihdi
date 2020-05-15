from odoo import models, fields, api

class StudentCalendar(models.Model):
	_name = 'agendaesi.studentcalendar' 
	_inherit = 'agendaesi.calendar'
	
	@api.model
	def _getAllowedGroupsId(self):
		return [
			('groups_id', 'in', [
				self.env.ref('agendaesi.agendaesi_student').id,
				self.env.ref('agendaesi.agendaesi_root').id
			])]
	
	attendee_ids = fields.Many2many("res.users", string="Attendees", domain=_getAllowedGroupsId)
	
	event_ids = fields.One2many("agendaesi.studentevent", "calendar_id", string="Events")

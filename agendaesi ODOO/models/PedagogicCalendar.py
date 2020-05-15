from odoo import models, fields, api

class PedagogicCalendar(models.Model):
	_name = 'agendaesi.pedagogiccalendar' 
	_inherit = 'agendaesi.calendar'
	
	@api.model
	def _getAllowedGroupsId(self):
		return [
			('groups_id', 'in', [
				self.env.ref('agendaesi.agendaesi_teacher').id,
				self.env.ref('agendaesi.agendaesi_root').id
			])]
	
	attendee_ids = fields.Many2many("res.users", string="Attendees", domain=_getAllowedGroupsId)
	
	event_ids = fields.One2many("agendaesi.pedagogicevent", "calendar_id", string="Events")

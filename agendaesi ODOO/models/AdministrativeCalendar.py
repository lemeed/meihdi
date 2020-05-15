from odoo import models, fields, api

class AdministrativeCalendar(models.Model):
	_name = 'agendaesi.administrativecalendar' 
	_inherit = 'agendaesi.calendar'
	
	@api.model
	def _getAllowedGroupsId(self):
		return [
			('groups_id', 'in', [
				self.env.ref('agendaesi.agendaesi_administrative').id,
				self.env.ref('agendaesi.agendaesi_root').id
			])]
	
	attendee_ids = fields.Many2many("res.users", string="Attendees", domain=_getAllowedGroupsId)
	
	event_ids = fields.One2many("agendaesi.administrativeevent", "calendar_id", string="Events")

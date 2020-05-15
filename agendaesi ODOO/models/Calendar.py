from odoo import models, fields, api
import datetime

class Calendar(models.Model):
	_name = "agendaesi.calendar"
	name = fields.Char(required=True, string="The name of the calendar")
	
	@api.model
	def _getAllowedGroupsId(self):
		return [('groups_id', 'in', map(lambda xmlId: self.env.ref(xmlId), self._allowed_groups))]
	
	manager_id = fields.Many2one("res.users", ondelete="set null", string="Manager of the event", index=True, default=lambda self: self.env.user.id, readonly=True) # default set to current user
	
	is_manager = fields.Boolean(string="is manager of the calendar", compute='_compute_is_manager', default=True)
	
	#@api.depends('is_manager')
	def _compute_is_manager(self):
		for record in self:
			record.is_manager = self.env.user.id == record.manager_id.id
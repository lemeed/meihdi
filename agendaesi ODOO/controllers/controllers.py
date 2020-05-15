# -*- coding: utf-8 -*-
from odoo import http
from odoo.http import request
import json
import logging
import datetime

_logger = logging.getLogger(__name__)

class Agendaesi(http.Controller):
	@http.route(route='/json/my-events', auth='user')
	def alist(self, **kw):
		current_date = datetime.datetime.now().strftime('%Y-%m-%d 00:00:00')
		matchEvent = request.env['agendaesi.event'].search([('start_date','>=', current_date)])
		resultSet = []
		for event in matchEvent:
			if request.uid in event.attendee_ids:
				someEvent = {
					'title':event.title,
					'place':event.place,
					'is_manager':event.is_manager,
					'max_attendee_number':event.max_attendee_number,
					'start_date':event.start_date,
					'end_date':event.end_date,
					'manager':event.manager_id.name
				}
				resultSet.append(someEvent)
		return json.dumps({'myEvents': resultSet})

# class Agendaesi(http.Controller):
#     @http.route('/agendaesi/agendaesi/', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/agendaesi/agendaesi/objects/', auth='public')
#     def list(self, **kw):
#         return http.request.render('agendaesi.listing', {
#             'root': '/agendaesi/agendaesi',
#             'objects': http.request.env['agendaesi.agendaesi'].search([]),
#         })

#     @http.route('/agendaesi/agendaesi/objects/<model("agendaesi.agendaesi"):obj>/', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('agendaesi.object', {
#             'object': obj
#         })
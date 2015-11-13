package org.inbio.pliniancore

import grails.converters.JSON;

class PliniancoreController {

	def pliniancoreService
	
    def index() { }
	
	def show() {
		if(!params.id){
			response.sendError(404, "Please provide an Id")
			return null
		}
		def result = pliniancoreService.show(params.id)
		result
		// esto es un comentario
	}
}

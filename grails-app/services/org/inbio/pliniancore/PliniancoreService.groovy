package org.inbio.pliniancore

import org.apache.commons.logging.Log;


class PliniancoreService {

	def solrService
	
    def show(String id) {
		log.debug("pliniancoreService")
		def result = solrService.search("*:*", "fq=taxonRecordId:" + id, null)
		result.results = result.results[0]
		result
    }
}

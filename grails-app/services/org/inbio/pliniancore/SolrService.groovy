package org.inbio.pliniancore

import groovy.json.JsonSlurper

class SolrService {
	
	def grailsApplication
	def solrServerUrl
	
	def solrService() {
		this.solrServerUrl = grailsApplication.config.indexBaseUrl
	}
	
	def search(q, queryString, requestedFacets){

//		def additionalParams = "&wt=json&facet=${!requestedFacets.isEmpty()}&facet.mincount=1"

		log.debug({['queryString': queryString, 'facets':requestedFacets]})
		
		if(requestedFacets){
			requestedFacets = "&facet.field=" + requestedFacets.join("&facet.field=")
		}
		else
			requestedFacets = ""

		if (queryString) {
			if (!q) {
				queryString = queryString.replaceFirst("q=", "q=*:*")
			} else if (q.trim() == "*") {
				queryString = queryString.replaceFirst("q=*", "q=*:*")
			}
			else {
				queryString += "&q=" + q
			}
		} else {
			queryString = "q=*:*"
		}
		
		// return json
		queryString += "&wt=json"
		
		log.debug(queryString)

		def queryResponse = new URL(grailsApplication.config.indexBaseUrl + "/select?" + queryString + requestedFacets).getText("UTF-8")
		def js = new JsonSlurper()
		def json = js.parseText(queryResponse)
		
		[
			totalRecords: json.response.numFound,
			facetResults: formatFacets(json.facet_counts?.facet_fields ?: []),
			results     : json.response.docs
		]
	}
	
	private def formatFacets(facetFields){
		def formatted = []
		facetFields.each { facetName, arrayValues ->
			def facetValues = []
			for (int i =0; i < arrayValues.size(); i+=2){
				facetValues << [label:arrayValues[i], count: arrayValues[i+1], fieldValue:arrayValues[i] ]
			}
			formatted << [
					fieldName: facetName,
					fieldResult: facetValues
			]
		}
		formatted
	}
				
}

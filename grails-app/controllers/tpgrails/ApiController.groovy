package tpgrails

import grails.converters.JSON
import grails.converters.XML

class ApiController {

    def annonce(){
        switch(request.getMethod()) {
            case "GET":
                if (!params.id) {
                    return response.status = 400
                }
                def annonceInstance= Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status= 404
                response.withFormat{
                    xml{ render annonceInstance as XML}
                    json{ render annonceInstance as JSON }
                }
                break
            case "PUT":
                if (!params.id) {
                    return response.status = 400
                }
                def annonceInstance= Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status= 404
                Annonce ac = request.JSON.id
                response.withFormat{
                    xml{ render annonceInstance as XML}
                    json{ render annonceInstance as JSON }
                }
                break
            case "PATCH":
                break
            case "DELETE":
                if (!params.id) {
                    return response.status = 400
                }
                def annonceInstance= Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status= 404
                annonceInstance.delete(flush: true)
                return response.status= 200
                break
            default:
                return response.status= 405
                break}
        return response.status= 406
    }

    def annonces(){
        switch(request.getMethod()) {
            case "GET":
                def annonceInstance= Annonce.list()
                if (!annonceInstance)
                    return response.status= 404
                response.withFormat{
                    xml{ render annonceInstance as XML}
                    json{ render annonceInstance as JSON }
                }
                break
            case "POST":
                def annonceInstance= Annonce.list()
                if (!annonceInstance)
                    return response.status = 400
                response.withFormat{
                    xml{ render annonceInstance as XML}
                    json{ render annonceInstance as JSON }
                }
                break
            default:
                return response.status= 405
                break}
        return response.status= 406
    }
}
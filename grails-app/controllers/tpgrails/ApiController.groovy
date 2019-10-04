package tpgrails

import grails.converters.JSON
import grails.converters.XML

class ApiController {

    AnnonceService annonceService

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
                def ac = request.JSON

                if (!ac.title|| !ac.description||!ac.validTill||!ac.state||ac.author)
                    return response.status = 400

                annonceInstance.properties=ac
                annonceInstance.save(flush:true)
                return response.status=200
                break

            case "PATCH":
                if (!params.id) {
                    return response.status = 400
                }
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404
                def ac = request.JSON
                annonceInstance.properties=ac
                annonceInstance.save(flush:true)
                return response.status=200
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
                def file = request.getFile("illustration")
                def author = User.get(params.author)
                if(!author || !file)
                    return response.status = 400

                def imageName = file.originalFilename
                file.transferTo(new File(grailsApplication.config.maconfig.asset_path + imageName))

                def annonceInstance = new Annonce(title: params.get("title"), description: params.get("description"),
                        validTill: params.get("validTill"), state: params.get("state"))

                annonceInstance.addToIllustrations(new Illustration(filename: imageName))
                annonceInstance.author = author
                annonceService.save(annonceInstance)

                return response.status = 200
                break
            default:
                return response.status= 405
                break}
        return response.status= 406
    }
}
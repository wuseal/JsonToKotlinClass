package wu.seal.jsontokotlinclass.server.models.routes.addhit

import wu.seal.jsontokotlinclass.server.models.routes.base.BaseResponse

class AddHitResponse(data: Void?, error: Boolean, errorCode: Int, message: String) : BaseResponse<Void>(data, error, errorCode, message) {
}
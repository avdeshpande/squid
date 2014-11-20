package api.resources

import java.util.UUID

/**
 * Created by abhijeetd on 10/24/14.
 */
case class UserResource(
                 firstName: String,
                 lastName: String,
                 email: String,
                 phone: String,
                 gender: String,
                 imageUrl: Option[String],
                 created: Long,
                 updated: Long,
                 id: UUID,
                 automobiles: List[AutomobileResource]
)

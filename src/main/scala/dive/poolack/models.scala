package dive.poolack

import dive.poolack.api.UserId
import dive.poolack.api.IssueId

final case class Issue(
    _id: IssueId,
    title: String,
    description: Option[String],
    assignee: Option[UserId]
)

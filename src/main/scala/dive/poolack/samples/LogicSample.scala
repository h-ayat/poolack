package dive.poolack.samples

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

class FE[E, A](in: Future[Either[E, A]]) {

  def map[B](f: A => B): FE[E, B] = ???

  def flatMap[B](f: A => FE[E, B]): FE[E, B] = ???

}

object FE {
  def pure[E, A](a: A): FE[E, A] = ???

  def fail[E, A](e: E): FE[E, A] = ???
}

object LogicSample {

  private def authorize(
      session: Session
  ): FE[Error, UserId] =
    ???

  private def getAccounts(userId: UserId): FE[Error, List[Account]] = ???

  private def getAccountById(
      accountId: AccountId
  ): FE[Error, Account] = ???

  private def getSourceAccount(
      userId: UserId,
      accountId: AccountId
  ): FE[Error, Account] = {
    getAccounts(userId).flatMap { p =>
      p.find(_.id == accountId) match {
        case None =>
          // new FE[Error, Account](Future.successful(Left(Forbidden)))
          FE.fail[Error, Account](Forbidden)
        case Some(value) =>
          // FE.pure(value)
          FE.pure[Error, Account](value)
        // new FE[Error, Account](Future.successful(Right(value)))
      }
    }
  }

  private def validateTransactionLimit(
      accountId: AccountId,
      amount: Rial
  ): FE[TransactionLimitExceeded, Unit] = ???

  private def reallyExecuteTransaction(
      from: AccountId,
      to: AccountId,
      amount: Rial
  ): Serial = ???

  def exec(req: Request): FE[Error, Ok] = {
    val o = for {
      userId <- authorize(req.session)
      account <- getSourceAccount(userId, req.myAccount)
      targetAccount <- getAccountById(req.target)
    } yield ()

    // val o = for {
    //   userId <- authorize(req.session)
    //   account <- getSourceAccount(userId, req.myAccount)
    //   targetAccount <- getAccountById(req.target)
    //   _ <-
    //     if (account.remaining.value >= req.amount.value)
    //       Right(())
    //     else
    //       Left(InsufficientFunds(account.remaining))
    //
    //   _ <- validateTransactionLimit(req.myAccount, req.amount)
    // } yield Ok(reallyExecuteTransaction(req.myAccount, req.target, req.amount))

    // authorize(req.session) match {
    //   case None => Response.UnAuthorized
    //   case Some(userId) =>
    //     val accounts = getAccounts(userId)
    //     accounts.find(_.id == req.myAccount) match {
    //       case None =>
    //         Response.Forbidden
    //       case Some(value) =>
    //         Response.Ok(???)
    //     }
    //
    // }
    ???
  }

}

final case class Session(id: String) extends AnyVal
final case class Rial(value: Long) extends AnyVal

final case class AccountId(value: String) extends AnyVal
final case class Account(id: AccountId, remaining: Rial)

final case class UserId(value: String) extends AnyVal

final case class Request(
    session: Session,
    amount: Rial,
    myAccount: AccountId,
    target: AccountId
)

final case class Serial(value: String) extends AnyVal

final case class Ok(serial: Serial)

sealed trait Error extends Product with Serializable
case object UnAuthorized extends Error
case object Forbidden extends Error
case class InvalidAccountId(accountId: AccountId) extends Error
case class InsufficientFunds(remaining: Rial) extends Error
case class TransactionLimitExceeded(rule: String) extends Error
// 1 unauthorized
// 2 folan
// 3

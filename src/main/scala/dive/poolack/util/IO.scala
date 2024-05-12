package dive.poolack.util

import scala.concurrent.Future
import scala.concurrent.ExecutionContext

class IO[+E, +A](val future: Future[Either[E, A]]) {

  def map[B](f: A => B): IO[E, B] = ???

  def transform[E2, A2](f: Either[E, A] => Either[E2, A2])(implicit
      ec: ExecutionContext
  ): IO[E2, A2] = new IO(future.map(f))

  def flatMap[E2 >: E, B](f: A => IO[E2, B])(implicit
      ec: ExecutionContext
  ): IO[E2, B] = {

    ???
  }

  def filterOrFail[E2 >: E](
      p: A => Boolean
  )(error: => E2)(implicit ec: ExecutionContext): IO[E2, A] = {

    transform {
      case Right(a) if p(a) => Right(a)
      case Right(_)         => Left(error)
      case Left(e)          => Left(e)
    }
  }

}

object IO {

  def success[A](a: A): IO[Nothing, A] = new IO(Future.successful(Right(a)))

  def fromFuture[A](a: Future[A])(implicit
      ec: ExecutionContext
  ): IO[Nothing, A] =
    new IO(a.map(Right.apply))
}

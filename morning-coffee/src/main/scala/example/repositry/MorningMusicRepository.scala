package example.repositry

import scala.concurrent.Future

trait MorningMusicRepository {
  def list(): Future[Seq[String]]
}

class MorningMusicRepositoryImpl extends MorningMusicRepository {
  override def list(): Future[Seq[String]] = ???
}

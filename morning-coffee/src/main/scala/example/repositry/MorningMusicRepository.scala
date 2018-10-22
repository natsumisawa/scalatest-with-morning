package example.repositry

import scala.concurrent.Future

trait MorningMusicRepository {
  def list(): Future[Seq[String]]

  def listByTsunku(titles: Seq[String]): Future[Seq[String]]
}

class MorningMusicRepositoryImpl extends MorningMusicRepository {
  override def list(): Future[Seq[String]] = ???

  override def listByTsunku(titles: Seq[String]): Future[Seq[String]] = ???
}

package example.service

import example.repositry.MorningMusicRepository
import com.google.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MorningMusicService @Inject()(morningMusicRepo: MorningMusicRepository) {

  def getNewTitles(): Future[Seq[String]] = {
    morningMusicRepo.list()
  }

  case class Title(value: String, isTsunku: Boolean)

  def isTsunku(titles: Seq[String]): Future[Seq[Title]] = {
    for {
      listByTsunku <- morningMusicRepo.listByTsunku(titles)
      result = titles.foldLeft(Seq[Title]()) {
        (acc, title) => {
          listByTsunku
            .find(_ == title)
            .map(t => Title(t, true) +: acc)
            .getOrElse(Title(title, false) +: acc)
        }
      }.reverse
    } yield result
  }
}

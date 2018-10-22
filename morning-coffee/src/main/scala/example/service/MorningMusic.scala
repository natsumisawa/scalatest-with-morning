package example.service

import example.repositry.MorningMusicRepository
import com.google.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MorningMusic @Inject()(morningMusicRepository: MorningMusicRepository) {
  def getNewTitle(): Future[String] = {
    Future("")
  }
}

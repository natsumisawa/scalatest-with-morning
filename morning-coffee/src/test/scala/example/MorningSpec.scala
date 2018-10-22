package example
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.Future
import example.repositry._
import example.service.MorningMusic

class MornigSpec extends AsyncFunSpec with Matchers with MockitoSugar {
  val titles = Seq("自由な国だから", "憧れのStress-Free", "フラリ銀座")
  describe("getNewMusic") {
    it("新曲が取得できる") {
      val morningMusicRepo = mock[MorningMusicRepository]
      when(morningMusicRepo.list()).thenReturn(Future(titles))
      val morningMusic = new MorningMusic(morningMusicRepo)
      val titlesFuture = morningMusic.getNewTitle()
      titlesFuture.map(title => {
        assert(title == titles)
      })
    }
  }
}

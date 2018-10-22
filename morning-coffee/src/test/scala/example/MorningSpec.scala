package example
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.Future
import example.repositry._
import example.service.MorningMusicService

class MorningSpec extends AsyncFunSpec with Matchers with MockitoSugar {

  case class Title(value: String, isTsunku: Boolean)

  describe("getNewMusic") {
    it("新曲が取得できる") {
      val titles = Seq("自由な国だから", "憧れのStress-Free", "フラリ銀座")
      val morningMusicRepo = mock[MorningMusicRepository]
      when(morningMusicRepo.list()).thenReturn(Future(titles))
      val morningMusic = new MorningMusicService(morningMusicRepo)
      val titlesFuture = morningMusic.getNewTitles()
      titlesFuture.map(title => {
        assert(title == titles)
      })
    }
  }

  describe("isTsunku") {
    // TODO なんでこれ落ちるの・・・
    it("つんく曲だったらtrueで返す") {
      val titles = Seq("自由な国だから", "憧れのStress-Free", "フラリ銀座", "泡沫サタデーナイト", "ナルシスかまってちゃん協奏曲")
      val morningMusicRepo = mock[MorningMusicRepository]
      val morningMusic = new MorningMusicService(morningMusicRepo)
      val expectedResult = Seq(
        Title("自由な国だから", true),
        Title("憧れのStress-Free", true),
        Title("フラリ銀座", true),
        Title("泡沫サタデーナイト", false),
        Title("ナルシスかまってちゃん協奏曲", true)
      )
      val tsunkuList = Seq("自由な国だから", "憧れのStress-Free", "フラリ銀座", "ナルシスかまってちゃん協奏曲")
      when(morningMusicRepo.listByTsunku(titles)).thenReturn(Future(tsunkuList))
      val resultFuture = morningMusic.isTsunku(titles)
      resultFuture.map(result => {
        assert(result == expectedResult)
      })
    }
  }
}

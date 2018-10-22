package example
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.Future
import example.repositry._
import example.service.{MorningMusicService, Title}

class MorningSpec extends AsyncFunSpec with Matchers with MockitoSugar {

  describe("getNewMusic") {
    it("新曲のリストが取得できる") {
      val expected = Seq("自由な国だから", "憧れのStress-Free", "フラリ銀座")
      val morningMusicRepo = mock[MorningMusicRepository]
      val morningMusic = new MorningMusicService(morningMusicRepo)

      when(morningMusicRepo.list()).thenReturn(Future(expected))

      val titlesFuture = morningMusic.getNewTitles
      // RepoをMockして、そのまま値を返すかどうかのテストを書く場合 意味の薄いテストになってしまうので
      // せめてverifyで そのメソッドが1回(バッチとかだったらN回) 動くことをテストする。
      verify(morningMusicRepo, times(1)).list()
      titlesFuture.map(actual => {
        assert(actual == expected)
      })
    }
  }

  describe("isTsunku") {
    // タイトルのリスト(List String) で良さそうだけども・・・
    it("つんく曲だったら真偽値を含んだタイトルのリストで返す") {
      // 何のタイトル化わからなかったので titles -> findTitles
      val findTitles = Seq("自由な国だから", "憧れのStress-Free", "フラリ銀座", "泡沫サタデーナイト", "ナルシスかまってちゃん協奏曲")
      val morningMusicRepo = mock[MorningMusicRepository]
      val morningMusic = new MorningMusicService(morningMusicRepo)
      // true, false だけ並んでいても読んだ人がわからないので、名前付きにする
      val expected = Seq(
        Title("自由な国だから", isTsunku = true),
        Title("憧れのStress-Free", isTsunku = true),
        Title("フラリ銀座", isTsunku = true),
        Title("泡沫サタデーナイト", isTsunku = false),
        Title("ナルシスかまってちゃん協奏曲", isTsunku = true)
      )

      val tsunkuList = Seq("自由な国だから", "憧れのStress-Free", "フラリ銀座", "ナルシスかまってちゃん協奏曲")
      when(morningMusicRepo.listByTsunku(findTitles)).thenReturn(Future(tsunkuList))

      val resultFuture = morningMusic.isTsunku(findTitles)
      resultFuture.map(actual => {
        assert(actual == expected)
      })
    }
  }
}

package example.service

import example.repositry.MorningMusicRepository
import com.google.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// case class がテストとServiceで２つあって それぞれから作られるインスタンスは別物なので比較しても一致しなかった
// case class は基本 classの中には作らないことを覚えておこう
// class, trait, case class 等は基本同じ階層になる
// def も 単体テストがしやすいように package private か public の二択になる(defのネストは避ける)
// Title(value: String) にしたほうが良い 意味合い的には、 TsunkuTitle という名前の方がふさわしくて　けどそれって汎用性低い
case class Title(value: String, isTsunku: Boolean)

class MorningMusicService @Inject()(morningMusicRepo: MorningMusicRepository) {

  def getNewTitles: Future[Seq[String]] = {
    morningMusicRepo.list()
  }

  // Note: isHogehoge は Booleanを返す命名
  // そもそも、つくんくじゃない曲は返す必要はあるの？
  // あとメソッド名に isTsunku, Titleにも isTsunku なのでそもそもわかりにくい
  def isTsunku(titles: Seq[String]): Future[Seq[Title]] = {
    for {
      listByTsunku <- morningMusicRepo.listByTsunku(titles)
      // for の中に長いロジックがあったら 名前を付けて package private にする！ 後から読んだときや他人は何のためのロジックかわからない！
      result = titles.foldLeft(Seq[Title]()) {
        (acc, title) => {
          listByTsunku
            .find(_ == title)
            .map(t => Title(t, isTsunku = true) +: acc)
            .getOrElse(Title(title, isTsunku = false) +: acc)
        }
      }.reverse
    } yield result
  }
}

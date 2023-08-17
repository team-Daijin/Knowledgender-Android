package dgsw.stac.knowledgender.ui.feature.main.childfeature.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.remote.Category
import dgsw.stac.knowledgender.ui.components.BannerView
import dgsw.stac.knowledgender.ui.components.CardList
import dgsw.stac.knowledgender.ui.feature.main.CARDNEWSDETAIL
import dgsw.stac.knowledgender.ui.feature.main.CardItem
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.IconClickedPath.BODY
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.IconClickedPath.CRIME
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.IconClickedPath.EQUALITY
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.IconClickedPath.HEART
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.IconClickedPath.RELATIONSHIP
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LighterBlack

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onNavigationRequested: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(viewModel)
        Body(viewModel, onNavigationRequested)
        Footer()
    }

}

data class IconData(val img: Int, val title: String,val category: String)
data class CardListData(val dataList: List<CardItem>, val topic: String, val des: String)

object IconClickedPath{
    val HEART = "Heart"
    val BODY = "Body"
    val CRIME = "Crime"
    val RELATIONSHIP = "Relationship"
    val EQUALITY = "Equality"
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Header(viewModel: HomeViewModel) {
    val bannerData = viewModel.bannerData

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
    ) {
        BannerView(bannerData)
    }
}

@Composable
fun Body(
    viewModel: HomeViewModel,
    onNavigationRequested: (String) -> Unit
) {
    val iconData = listOf(
        IconData(R.drawable.heart, "마음",HEART),
        IconData(R.drawable.body, "신체",BODY),
        IconData(R.drawable.crime, "폭력",CRIME),
        IconData(R.drawable.relationship, "관계",RELATIONSHIP),
        IconData(R.drawable.equality, "평등",EQUALITY)
    )
    val cardData by produceState(initialValue = emptyList<CardListData>(), producer = {
        value = listOf(
            CardListData(
                viewModel.getCardCategory(Category.HEART), "마음 상담소로 오세요", "내 안에 숨어있는 마음상담소로 초대합니다!"
            ), CardListData(
                viewModel.getCardCategory(Category.BODY), "나만 몰랐던 나의 몸", "나조차도 모르고 있었던 나의 몸 속 비밀"
            ), CardListData(
                viewModel.getCardCategory(Category.RELATIONSHIP),
                "너와 나의 연결고리",
                "즐겁고 행복한 우리의 관계를 건강하게 유지하는 법"
            ), CardListData(
                viewModel.getCardCategory(Category.CRIME),
                "나를 확실하게 지키는 법",
                "폭력으로부터 나를 올바른 방법으로 보호해봅시다"
            ), CardListData(
                viewModel.getCardCategory(Category.EQUALITY), "세상에 같은 사람은 없다", "차이는 틀린 것이 아닌 다른 것!"
            )
        )
    })

    Icons(iconData, onNavigationRequested)
    CardLists(cardData,onNavigationRequested)
}

@Composable
fun Footer() {
}
@Composable
fun Icon(data: IconData, onNavigateTo: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = {
            onNavigateTo(data.category)
//            onNavigationRequested = { navController.navigate( CardNewsStringToEnum(data.title) ) }
            Log.d("TAG", "${data.title}: 페이지 ${data.title} 클릭 ")
        }) {
            Image(
                painter = painterResource(data.img),
                contentDescription = data.title,
                modifier = Modifier.size(60.dp)
            )
        }
        Text(
            text = data.title,
            style = TextStyle(fontSize = 14.sp, color = BaseBlack, fontWeight = FontWeight.Medium)
        )
    }
    Spacer(modifier = Modifier.width(10.dp))
}

@Composable
fun Icons(dataList: List<IconData>, onNavigateTo: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        dataList.forEach {
            Icon(it, onNavigateTo = onNavigateTo)
        }
    }
}

@Composable
fun Card(data: CardListData,onRowItemClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = data.topic, modifier = Modifier.padding(5.dp), style = TextStyle(
                fontSize = 20.sp,
                color = DarkestBlack,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = data.des, modifier = Modifier.padding(5.dp), style = TextStyle(
                fontSize = 14.sp,
                color = LighterBlack,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.width(120.dp))
        CardList(data.dataList,onRowItemClicked)
    }
}

@Composable
fun CardLists(data: List<CardListData>,onNavigationRequested: (String) -> Unit) {
    data.forEach {
        Card(it) { data ->
            onNavigationRequested("$CARDNEWSDETAIL/{$data}")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview4() {
//    KnowledgenderAndroidTheme {
//        HomeScreen(viewModel = MainViewModel(), onNavigateTo = )
//    }
//}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomePreview() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            HomeScreen(viewModel = hiltViewModel(), onNavigationRequested = {})
        }
    }
}
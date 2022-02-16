<template>
  <div id="navbar">
    <div class="navbar-left">
      <router-link :to="{ name: 'Main' }">
        <img src="@/assets/mainlogo.png" alt="mainlogo" />
      </router-link>
      <!-- <auto-complete></auto-complete> -->
      <div class="searchbar-box" :class="{ showNav: isShowNav }">
        <input
          type="text"
          class="searchbar"
          placeholder="작품명을 입력하세요."
          list="artwork-options"
          v-model="title"
          @keyup.enter="SearchResult(title)"
        />
        <datalist id="artwork-options" style="display: none">
          <!-- 전체 작품 title을 option value에 넣으면 자동완성 만들기 가능하지 않을까? -->
          <option value="" />
        </datalist>
        <magnify
          class="icon searchbar-icon"
          @click="SearchResult(title)"
        ></magnify>
      </div>
    </div>
    <nav :class="{ showNav: isShowNav }">
      <!-- 사용자 로그인 전 -->
      <!-- <ul v-if="!isLogin">
        <li @click="closeNavBar">
          <router-link :to="{ name: 'Login' }">로그인</router-link>
        </li>
        <li @click="closeNavBar">
          <router-link :to="{ name: 'SignUp' }">회원가입</router-link>
        </li>
      </ul> -->
      <!-- 사용자 로그인 후 -->
      <ul>
        <li @click="closeNavBar">
          <router-link :to="{ name: 'ArtworkCreate' }">그림 올리기</router-link>
        </li>
        <li class="tooltip" @click="closeNavBar">
          <router-link :to="{ name: 'Profile', params: { id: userInfo.id } }"
            ><account-circle-outline
              class="icon navbar-icon"
            ></account-circle-outline>
            <div class="navbar-text">내 정보</div></router-link
          >
          <span class="tooltip-text">내 정보</span>
        </li>
        <li class="tooltip" @click="closeNavBar">
          <logout class="icon navbar-icon" @click="onClickLogout"></logout>
          <div class="navbar-text logout" @click="onClickLogout">로그아웃</div>
          <span class="tooltip-text">로그아웃</span>
        </li>
      </ul>
    </nav>
    <text-search class="icon navbar-toggle" @click="toggleNavBar"></text-search>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Magnify, AccountCircleOutline, Logout, TextSearch } from "mdue";
import { mapState, mapActions } from "vuex";
import AutoComplete from "@/components/layout/child/AutoComplete.vue"

const accountsStore = "accountsStore";

export default defineComponent({
  name: "NavBar",
  components: {
    Magnify,
    AccountCircleOutline,
    Logout,
    TextSearch,
    // AutoComplete,
  },
  data() {
    return {
      isShowNav: false,
      title: "",
      artworkList: "",
    };
  },
  computed: {
    ...mapState(accountsStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapActions(accountsStore, ["getLogout"]),
    toggleNavBar() {
      this.isShowNav = !this.isShowNav;
    },
    closeNavBar() {
      this.isShowNav = false;
    },
    // 로그아웃 처리
    async onClickLogout() {
      await this.getLogout();
      this.$router.push({ name: "Login" });
    },
    // 검색어 전달
    SearchResult(title: string) {
      console.log(this.$route.query.artworkList);
      if (title !== "") {
        this.$router.push({
          name: "SearchResult",
          query: {
            title: this.title,
          },
        });
        this.title = "";
      } else {
        alert("검색어를 입력해주세요!");
      }
    },
  },
});
</script>

<style lang="scss" scoped>
#navbar {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: $size-large $size-big;
  border-bottom: 1px solid $grey;
}

.navbar-left {
  display: flex;
  align-items: center;
  width: 70%;
}

img {
  width: 150px;
  margin-right: $size-large;
  cursor: pointer;
}

.searchbar-box {
  position: relative;
  display: flex;
  align-items: center;
  width: 100%;
  .searchbar {
    width: 100%;
    padding: $size-small;
    margin: $size-micro $size-large;
    border: 1px solid $grey;
    font-size: $font-regular;
  }

  .searchbar-icon {
    position: absolute;

    right: $size-big;
    color: $dark-grey;
  }
}

ul {
  display: flex;
  align-items: center;
  li {
    padding: 0 $size-regular;
    cursor: pointer;
    font-size: $font-medium;
  }
}

.icon {
  font-size: $font-large;
  cursor: pointer;
}

.navbar-toggle {
  position: absolute;
  right: 0;
  display: none;
}

/* 아이콘에 마우스 hover 시 정보가 보이는 tooltip 설정 */
.tooltip {
  position: relative;
  .tooltip-text {
    visibility: hidden;
    opacity: 0;
    width: 80px;
    padding: $size-regular;
    background-color: $black;
    color: $white;
    text-align: center;
    border-radius: $size-small;
    position: absolute;
    z-index: 99;
    top: 130%;
    left: -30%;
    transition: all 0.2s ease-in;
  }
  .tooltip-text::after {
    content: " ";
    position: absolute;
    border-style: solid;
    border-width: 5px;
    border-color: transparent transparent $black transparent;
    bottom: 100%;
    left: 50%;
    margin-left: -3px;
  }
  &:hover .tooltip-text {
    visibility: visible;
    opacity: 1;
  }
}

.navbar-text {
  display: none;
}

/* 모바일 크기에서 적용되는 스타일 */
@media screen and (max-width: 640px) {
  .showNav.searchbar-box {
    display: flex;
  }

  nav.showNav {
    display: block;
  }

  #navbar {
    flex-direction: column;
    padding: 0;
  }

  .navbar-left {
    flex-direction: column;
    width: 100%;
  }

  img {
    width: 130px;
    margin: $size-medium $size-large;
  }

  .searchbar-box {
    display: none;
  }

  nav {
    width: 100%;
    display: none;
  }

  ul {
    flex-direction: column;
    width: 100%;
    li {
      width: 100%;
      a {
        padding: $size-large;
        display: block;
        width: 100%;
        text-align: center;
      }
    }
  }

  .navbar-toggle {
    display: block;
    font-size: $font-large;
    padding: 1.5rem;
  }

  .navbar-icon {
    display: none;
  }

  .tooltiptext {
    display: none;
  }

  .navbar-text {
    display: block;
    cursor: pointer;
    &.logout {
      padding: $size-large;
      text-align: center;
    }
  }
}
</style>

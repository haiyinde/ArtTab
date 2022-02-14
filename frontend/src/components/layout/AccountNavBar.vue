<template>
  <div id="navbar">
    <div class="navbar-left">
      <router-link :to="{ name: 'Login' }">
        <img src="@/assets/mainlogo.png" alt="mainlogo" />
      </router-link>
    </div>
    <nav :class="{ showNav: isShowNav }">
      <ul v-if="!isLogin">
        <li @click="closeNavBar">
          <router-link :to="{ name: 'Login' }">로그인</router-link>
        </li>
        <li @click="closeNavBar">
          <router-link :to="{ name: 'SignUp' }">회원가입</router-link>
        </li>
      </ul>
    </nav>
    <text-search class="icon navbar-toggle" @click="toggleNavBar"></text-search>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { TextSearch } from "mdue";
import { mapState, mapActions } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "NavBar",
  components: {
    TextSearch,
  },
  data() {
    return {
      isShowNav: false,
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

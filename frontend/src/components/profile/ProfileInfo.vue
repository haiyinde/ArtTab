<template>
  <div class="black-bg" v-if="isOpen == true">
    <div class="white-bg">
      <h4>내 정보 수정 modal이 될 것</h4>
      <img src="https://via.placeholder.com/150/92c952" />
      <p>닉네임닉네임닉네임</p>
      <p>
        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Nostrum,
        maiores tenetur. Incidunt nihil vitae aliquid totam ex maxime sint
        perferendis.
      </p>
      <button @click="changeProfilePic">프로필 사진 변경</button>
      <button @click="doneEditInfo">완료</button>
    </div>
  </div>
  <div>
    <p>Profile Info Component</p>
    <div class="container">
      <div class="profile">
        <div class="profile-image">
          <img
            src="https://via.placeholder.com/150/92c952"
            alt="profile image"
          />
        </div>
        <div class="profile-user-settings">
          <h1 class="profile-user-name">닉네임닉네임닉네임</h1>
          <!-- 이메일 조회 빼도 될 듯? -->
          <p class="profile-user-email">email@email.com</p>
          <button class="btn profile-edit-btn" @click="openEditModal">
            내 정보 수정
          </button>
          <follow-button
            class="btn profile-edit-btn"
            :followed="!valid"
            @click="handleFollow"
          ></follow-button>
        </div>
        <div class="profile-stats">
          <ul>
            <li><span class="profile-stat-count">164</span> 게시물</li>
            <li><span class="profile-stat-count">188</span> 팔로워</li>
            <li><span class="profile-stat-count">206</span> 팔로잉</li>
          </ul>
        </div>
        <div class="profile-bio">
          <p>
            Lorem, ipsum dolor sit amet consectetur adipisicing elit. Nostrum,
            maiores tenetur. Incidunt nihil vitae aliquid totam ex maxime sint
            perferendis.
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import FollowButton from "./child/FollowButton.vue";

export default defineComponent({
  data() {
    return {
      valid: true,
      isOpen: false,
    };
  },
  components: {
    FollowButton,
  },
  methods: {
    handleFollow() {
      this.valid = !this.valid;
    },
    openEditModal() {
      console.log("내 정보 수정 modal");
      this.isOpen = true;
    },
    closeEditModal() {
      this.isOpen = false;
    },
    doneEditInfo() {
      // 정보 수정 담아서 BE로 보내는 method
      this.closeEditModal();
    },
    changeProfilePic() {
      // 프로필 사진 변경 정보 담아서 BE로 보내는 method
      this.closeEditModal();
    },
  },
});
</script>

<style scoped lang="scss">
// my profile info edit modal
.black-bg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  padding: 20px;
}
.white-bg {
  width: 100%;
  background: white;
  border-radius: 8px;
  padding: 20px;
}

// profile
:root {
  font-size: 10px;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

body {
  font-family: "Open Sans", Arial, sans-serif;
  min-height: 100vh;
  background-color: #fafafa;
  color: #262626;
  padding-bottom: 3rem;
}

img {
  display: block;
}

.container {
  max-width: 1200px;
  min-width: 320px;
  margin: 0 auto;
  padding: 0 1rem;
  border: 1px solid $grey;
}

.btn {
  display: inline-block;
  font: inherit;
  background: none;
  border: none;
  color: inherit;
  padding: 0;
  cursor: pointer;
}

.btn:focus {
  outline: 0.5rem auto #4d90fe;
}

.visually-hidden {
  position: absolute !important;
  height: 1px;
  width: 1px;
  overflow: hidden;
  clip: rect(1px, 1px, 1px, 1px);
}

/* Profile Section */

.profile {
  padding: 5rem 0;
}

.profile::after {
  content: "";
  display: block;
  clear: both;
}

.profile-image {
  float: left;
  width: calc(33.333% - 1rem);
  display: flex;
  justify-content: center;
  align-items: center;
}

.profile-image img {
  border-radius: 50%;
}

.profile-user-settings,
.profile-stats,
.profile-bio {
  float: left;
  width: calc(66.666% - 2rem);
}

.profile-user-settings {
  margin-top: 1.1rem;
}

.profile-user-name {
  display: block;
  // display: block;
  // display: none;
  font-size: $font-large;
  font-weight: $weight-semi-bold;
}

.profile-user-email {
  display: inline-block;
}

.profile-edit-btn {
  font-size: $font-regular;
  line-height: 1.8;
  border: 0.1rem solid $black;
  border-radius: 0.3rem;
  padding: 0 2.4rem;
  margin-left: 2rem;
  // hover시 색상 전환 천천히
  transition: border-color 0.5s, background-color 0.5s, color 0.5s;
  // hover시 색상 전환
  &:hover {
    background-color: $black;
    color: $white;
    border-color: transparent;
  }
}

.profile-stats {
  margin-top: 2.3rem;
}

.profile-stats li {
  display: inline-block;
  font-size: 1.6rem;
  line-height: 1.5;
  margin-right: 4rem;
  cursor: pointer;
}

.profile-stats li:last-of-type {
  margin-right: 0;
}

.profile-bio {
  font-size: 1.6rem;
  font-weight: 400;
  line-height: 1.5;
  margin-top: 2.3rem;
}

.profile-stat-count,
.profile-edit-btn {
  font-weight: $weight-semi-bold;
}

/* Media Query */
// mobile

@media screen and (max-width: 40rem) {
  .profile {
    display: flex;
    flex-wrap: wrap;
    padding: 4rem 0;
  }

  .profile::after {
    display: none;
  }

  .profile-image,
  .profile-user-settings,
  .profile-bio,
  .profile-stats {
    float: none;
    width: auto;
  }

  .profile-image img {
    width: 7.7rem;
  }

  .profile-user-settings {
    flex-basis: calc(100% - 10.7rem);
    display: flex;
    flex-wrap: wrap;
    margin-top: 1rem;
  }

  .profile-user-name {
    display: block;
    font-size: $font-large;
  }

  .profile-user-email {
    display: block;
    // font-size: $font-large;
  }

  .profile-edit-btn {
    order: 1;
    padding: 0;
    text-align: center;
    margin-top: 1rem;
  }

  .profile-edit-btn {
    margin-left: 0;
  }

  .profile-bio {
    font-size: 1.4rem;
    margin-top: 1.5rem;
  }

  .profile-edit-btn,
  .profile-bio,
  .profile-stats {
    flex-basis: 100%;
  }

  .profile-stats {
    order: 1;
    margin-top: 1.5rem;
  }

  .profile-stats ul {
    display: flex;
    text-align: center;
    padding: 1.2rem 0;
    border-top: 0.1rem solid #dadada;
    border-bottom: 0.1rem solid #dadada;
  }

  .profile-stats li {
    font-size: 1.4rem;
    flex: 1;
    margin: 0;
  }

  .profile-stat-count {
    display: block;
  }
}

/* CSS grid */

@supports (display: grid) {
  .profile {
    display: grid;
    grid-template-columns: 1fr 2fr;
    grid-template-rows: repeat(3, auto);
    grid-column-gap: 3rem;
    align-items: center;
  }

  .profile-image {
    grid-row: 1 / -1;
  }

  .gallery {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(22rem, 1fr));
    grid-gap: 2rem;
  }

  .profile-image,
  .profile-user-settings,
  .profile-stats,
  .profile-bio,
  .gallery-item,
  .gallery {
    width: auto;
    margin: 0;
  }

  @media (max-width: 40rem) {
    .profile {
      grid-template-columns: auto 1fr;
      grid-row-gap: 1.5rem;
    }

    .profile-image {
      grid-row: 1 / 2;
    }

    .profile-user-settings {
      display: grid;
      grid-template-columns: auto 1fr;
      grid-gap: 1rem;
    }

    .profile-edit-btn,
    .profile-stats,
    .profile-bio {
      grid-column: 1 / -1;
    }

    .profile-user-settings,
    .profile-edit-btn,
    .profile-bio,
    .profile-stats {
      margin: 0;
    }
  }
}
</style>

<template>
  <div class="artist-box">
    <div class="profile">
      <router-link :to="{ name: 'Profile', params: { id: artwork.memberId } }">
        <div class="profileimg-box">
          <img :src="artwork.imgUrl" alt="작가 프로필 이미지" />
        </div>
      </router-link>
      <div class="profile-detail">
        <div class="nickname">{{ artwork.nickName }}</div>
        <div class="additonal-info">
          <account-heart class="icon"></account-heart>
          <p>{{ artwork.followerNum }}</p>
          <camera-burst class="icon"></camera-burst>
          <p>{{ artwork.artworkNum }}</p>
        </div>
      </div>
    </div>

    <div class="artworks">
      <router-link
        :to="{ name: 'ArtworkDetail', params: { id: artwork.memberId } }"
        v-for="info in artwork.artworkInfo"
        :key="info.artworkId"
      >
        <div class="artworkimg-box">
          <img :src="info.saveFolder" alt="작품 이미지" />
        </div>
      </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { AccountHeart, CameraBurst } from "mdue";

export default defineComponent({
  name: "ArtistItem",
  props: {
    artwork: {
      type: Object,
      required: true,
    },
  },
  components: {
    AccountHeart,
    CameraBurst,
  },
});
</script>

<style lang="scss" scoped>
.artist-box {
  box-shadow: rgba(50, 50, 93, 0.25) 0px 6px 12px -2px,
    rgba(0, 0, 0, 0.3) 0px 3px 7px -3px;
  padding: $size-large;
  margin: $size-regular;
  width: 618px;
}
.profile {
  display: flex;
  .profileimg-box {
    width: 3rem;
    height: 3rem;
    border-radius: 50%;
    overflow: hidden;
    margin-right: $size-small;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      cursor: pointer;
    }
  }
  .profile-detail {
    display: flex;
    flex-direction: column;
    .nickname {
      font-size: $size-large;
    }
  }

  .additonal-info {
    display: flex;
    align-items: center;
    .icon {
      margin: $size-small;
    }
  }
}
.artworks {
  display: flex;
  justify-content: center;
  .artworkimg-box {
    width: 8rem;
    height: 8rem;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      cursor: pointer;
    }
    img:hover {
      box-shadow: 10px 10px 10px -5px rgba(25, 42, 70, 0.2);
      transition: all 0.2s linear;
      transform: scale(1.1);
    }
  }
}
</style>

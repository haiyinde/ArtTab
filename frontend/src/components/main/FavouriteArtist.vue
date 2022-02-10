<template>
  <!-- <image-list></image-list> -->
  <div class="inner">
    <div class="divide-box">
      <div class="box-likeartist">
        <div class="profile-status">
          <div class="image-profile">
            <img
              src="../../assets/images/test1.jpg"
              height="52"
              width="52"
              alt="프로필사진"
            />
          </div>
          <div class="nickname">아이디</div>
          <div class="status">
            <camera-burst></camera-burst>
            <span>13</span>
            <account-heart></account-heart>
            <span>13</span>
          </div>
        </div>
        <div class="box-image">
          <div class="image-first">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
          <div class="image-second">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
          <div class="image-third">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
          <div class="image-fourth">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
        </div>
      </div>
      <div class="box-likeartist">
        <div>
          <div class="image-profile">
            <img
              src="../../assets/images/test1.jpg"
              height="52"
              width="52"
              alt="프로필사진"
            />
          </div>
          <div class="nickname">아이디</div>
          <div class="status">
            <camera-burst></camera-burst>
            <span>13</span>
            <account-heart></account-heart>
            <span>13</span>
          </div>
        </div>
        <div class="box-image">
          <div class="image-first">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
          <div class="image-second">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
          <div class="image-third">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
          <div class="image-fourth">
            <img
              src=""
              height="124"
              width="124"
              alt="작품1"
              onerror="this.style.display='none'"
            />
          </div>
        </div>
      </div>
    </div>
    <divs class="divide-box">
      <div class="box-likeartist">
        <div>
          <div class="image-profile">
            <img
              src="../../assets/images/test1.jpg"
              height="52"
              width="52"
              alt="프로필사진"
            />
          </div>
          <div class="nickname">아이디</div>
          <div class="status">
            <camera-burst></camera-burst>
            <span>13</span>
            <account-heart></account-heart>
            <span>13</span>
          </div>
        </div>
        <div class="box-image">
          <div class="image-first">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
          <div class="image-second">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
          <div class="image-third">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
          <div class="image-fourth">
            <img
              src="../../assets/images/test2.jpg"
              height="124"
              width="124"
              alt="작품1"
            />
          </div>
        </div>
      </div>
    </divs>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { AccountHeart } from "mdue";
import { CameraBurst } from "mdue";
import ImageList from "@/components/ImageList.vue";

export default defineComponent({
  name: "MainArtist",
  components: {
    AccountHeart,
    CameraBurst,
    // ImageList,
  },
});
</script>

<style lang="scss" scoped>
.container {
  font-size: 30px;
}

.inner {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  flex-direction: row;
}

.divide-box {
  display: flex;
  justify-content: flex-start;
  margin-top: 30px;
  margin-bottom: 0px;
  align-items: center;
}

.box-likeartist {
  width: 618px;
  background-color: #fff;
  flex-direction: column;
  border-radius: 9px;
  padding: 0.5rem 1rem;
  margin: 0 1rem;
  box-shadow: 10px 10px 10px -5px rgba(25, 42, 70, 0.2);
}

.box-image {
  margin: 0 auto;
  padding-top: 10px;
  align-items: center;
  justify-content: flex-start;
}

.box-image > div {
  max-width: 100%;
  height: auto;
  display: inline-block;
  margin-left: 10px;
  height: 124px;
  width: 124px;
}

.box-image > div:hover {
  box-shadow: 10px 10px 10px -5px rgba(25, 42, 70, 0.2);
  transition: all 0.2s linear;
  transform: scale(1.1);
}

.nickname {
  font-size: larger;
  font-weight: bold;
  color: #545e6f;
  margin-bottom: 0.2rem;
  display: inline-block;
  position: relative;
  top: -30px;
  left: 8px;
}

.status {
  display: inline-block;
  position: relative;
  top: -0px;
  left: -50px;
}

span {
  margin: 0 3px;
  position: relative;
  top: -1px;
}

/* 프로필 이미지 */
.image-profile {
  margin-left: 25px;
  display: inline-block;
}

.arts {
  max-height: 124px;
  max-width: 124px;
}

/* 반응형 1200px 이상이면 안넓어짐 */
@media only screen and (min-width: 1300px) {
  .inner {
    max-width: 1300px;
  }
}

/* 1261px 이하면 열로 정렬 */
@media (max-width: 1262px) {
  .divide-box {
    flex-direction: column;
  }
  .box-likeartist {
    margin-top: 90px;
  }
}

@media (max-width: 640px) {
  .box-image {
    display: none;
  }
  .profile-status {
    display: inline;
    justify-items: center;
  }
}
</style>

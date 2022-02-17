<template>
  <div>
    <div class="class-list">
      <div class="class-card">
        <label for="file">
          <div class="imgbox">
            <img
              v-if="tempimage"
              class="tempimage"
              :src="tempimage"
              style="cursor: pointer"
            />
            <img
              v-else
              :src="require(`@/assets/images/plus-circle.png`)"
              class="altimg"
            />
          </div>
        </label>
        <div class="input-div" style="display: none">
          <input
            ref="artworkImg"
            id="file"
            type="file"
            accept="image/*"
            @change="onInputImage"
          />
        </div>
        <div class="button" onclick="onclick=document.all.file.click()"></div>
        <div class="class-container">
          <div class="class-title">
            <input
              type="text"
              class="class-input-title"
              placeholder="제목을 입력하세요."
              maxlength="31"
              v-model="title"
            />
          </div>
          <hr class="hr" noshade />
          <div class="class-subtitle">
            <textarea
              class="textarea"
              placeholder="내용을 입력하세요."
              v-model="description"
              style="maxlength='300'"
            ></textarea>
            <!-- 내용입력 스크롤 자동으로 늘어나는거 구현..? -->
          </div>
        </div>
      </div>
      <div class="button-box">
        <button class="btn-white" @click="updateArtwork">수정하기</button>
        <button class="class-butten" @click="modalPop">삭제하기</button>
      </div>
    </div>
  </div>
  <!-- 모달 -->
  <div class="modal">
    <div class="modal-body">
      <span>정말 삭제 하시겠습니까?</span>
      <div class="modal-butten">
        <button class="btn-white" @click="artworkDelete">삭제하기</button>
        <button class="class-butten" @click="modaloff">돌아가기</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import artworkAPI from "@/apis/artworkAPI";
import { defineComponent } from "vue";
import ResponseData from "@/types/ResponseData";
import { mapState } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "ArtworkUpdate",
  components: {},
  data() {
    // 상세정보에서 받아온 수정해야할 그림 정보
    return {
      file: "",
      // writerid: this.$route.query.artworkId as string,
      id: Number(this.$route.query.artworkId),
      title: this.$route.query.title as string,
      description: this.$route.query.description as string,
      tempimage: "",
    };
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
  },
  methods: {
    onInputImage(e: any) {
      let files = e.target.files[0];
      console.log(files);
      this.file = files;
      //이미지 프리뷰
      this.tempimage = URL.createObjectURL(files);
    },
    modalPop() {
      const modal: any = document.querySelector(".modal");
      modal.style.display = "block";
      return;
    },
    async artworkDelete() {
      await artworkAPI
        .deleteArtwork(this.id)
        .then((res: ResponseData) => {
          console.log(res.data);
        })
        .catch((e) => {
          alert("오류가 발생하여 삭제가 취소되었습니다.");
          console.log(e);
        });
      this.$router.replace("/");
    },
    modaloff() {
      const modal: any = document.querySelector(".modal");
      modal.style.display = "none";
      return;
    },
    updateArtwork() {
      if (this.file === "" || this.title === "" || this.description === "") {
        alert("그림, 제목, 내용을 모두 입력하세요");
      } else {
        const artwork = new FormData();
        artwork.append("file", this.file);
        artwork.append("title", this.title);
        artwork.append("writerId", this.userInfo.id);
        artwork.append("description", this.description);
        artworkAPI.updateArtwork(this.id, artwork).then((res) => {
          this.$router.replace("/");
        });
      }
    },
  },
});
</script>

<style lang="scss" scoped>
@import "~@/assets/css/common.scss";

.modal {
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  display: none;

  background-color: rgba(0, 0, 0, 0.4);
}

.modal.show {
  display: block;
}

.modal-body {
  display: inline-block;
  position: absolute;
  top: 50%;
  bottom: 50%;
  left: 50%;

  width: 400px;
  height: 250px;

  padding: 40px;
  padding-top: 60px;

  border-radius: 10px;
  background-color: white;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
  text-align: center;

  transform: translateX(-50%) translateY(-50%);
}

.modal-body > span {
  font-size: $font-large;
  font-weight: bold;
}

.modal-butten {
  display: flex;
  margin-top: 2rem;
}

.class-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  list-style: none;
  justify-content: center;
  margin-left: 3rem;
  margin-right: 3rem;
  margin-top: 4rem;
}

.class-card {
  width: 800px;
  height: 100%;
  box-shadow: 10px 10px 10px 10px rgh;
  border-radius: 8px;
  border: 1px solid black;
}

.class-image {
  width: 100%;
  border-radius: 8px;
}

.class-container {
  width: 100%;
  /* padding-bottom: 2rem; */
  border-radius: 8px;
  height: 100%;
}

.hr {
  width: 99.9%;
  border: 0;
  height: 1px;
  background: black;
}

.class-title {
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipse;
  margin-bottom: 0.5rem;
}

.class-input-title {
  width: 100%;
  padding-bottom: 0.5rem;
  padding-top: 0.5rem;
  font-size: $font-large;
  font-weight: bold;
  padding-left: 10px;
  padding-right: 10px;
}

.textarea {
  width: 100%;
  padding: 0;
  border-width: 0;
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  border-radius: 8px;
  min-height: 5rem;
  // overflow-y: hidden;
  resize: none;
  font-size: $font-medium;
  padding-left: 10px;
}

.class-butten {
  display: inline-block;
  justify-content: center;
}

.button-box {
  width: 60%;
  display: flex;
  align-items: center;
}

button {
  width: 40%;
  padding: $size-small;
  margin: 2rem;
  font-size: $font-medium;
  background-color: black;
  color: white;
  border-radius: $size-micro;
  &.btn-white {
    // margin-top: $size-medium;
    background-color: $white;
    color: $black;
    border: 1px solid $black;
  }
}

.inputimage {
  border: none;
  border-bottom: 1px solid black;
  text-align: center;
  font-size: 20px;
}

.tempimage {
  display: flex;
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.altimg {
  display: flex;
  width: 55px;
  height: 55px;
  cursor: pointer;
}

.altimg:hover {
  transition: all 0.2s linear;
  transform: scale(1.3);
}

.imgbox {
  display: flex;
  width: 798px;
  height: 400px;
  justify-content: center;
  align-items: center;
}

@media screen and (min-width: 1300px) {
  .button-box {
    width: 725px;
  }
}

@media screen and (max-width: 800px) {
  .class-card {
    width: 400px;
  }
  .imgbox {
    width: 399px;
  }
  .button-box {
    width: 330px;
  }
}

@media screen and (max-width: 380px) {
  .imgbox {
    width: 300px;
  }
  .class-card {
    width: 300px;
  }
  .modal-body {
    width: 300px;
  }
}
</style>

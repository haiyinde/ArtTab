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
              v-model="desc"
              maxlength="300"
            ></textarea>
          </div>
        </div>
      </div>
      <button class="class-butten" @click="addArtwork">등록하기</button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import artworkAPI from "@/apis/artworkAPI";
import { mapState } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "ArtworkCreate",
  components: {},
  data() {
    return {
      file: "",
      desc: "",
      title: "",
      tempimage: "",
    };
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
  },
  methods: {
    onInputImage(e: any) {
      let files = e.target.files[0];
      this.file = files;
      //이미지 프리뷰
      this.tempimage = URL.createObjectURL(files);
    },
    // image file은 form data로 보내야함
    addArtwork() {
      if (this.file === "" || this.title === "" || this.desc === "") {
        alert("그림, 제목, 내용을 모두 입력하세요");
      } else {
        const artwork = new FormData();
        artwork.append("file", this.file);
        artwork.append("title", this.title);
        artwork.append("writerId", this.userInfo.id);
        artwork.append("description", this.desc);
        artworkAPI.addArtwork(artwork).then((res) => {
          if (res.data === "success") {
            this.$router.replace("/");
          } else {
            alert("작품 등록 중 오류가 발생했습니다.");
          }
        });
      }
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/common.scss";

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
  display: flex;
  justify-content: center;
}

button {
  width: 25%;
  padding: $size-small;
  margin: 2rem;
  font-size: $font-medium;
  background-color: black;
  color: white;
  border-radius: $size-micro;
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

@media (max-width: 800px) {
  .class-card {
    width: 400px;
  }
  .imgbox {
    width: 399px;
  }
}

@media (max-width: 380px) {
  .imgbox {
    width: 300px;
  }
  .class-card {
    width: 300px;
  }
  .class-butten {
    font-size: 12px;
  }
}
</style>

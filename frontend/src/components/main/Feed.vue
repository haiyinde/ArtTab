<template>
  <div>
    <div class="container">
      <masonry-wall
        :items="items"
        :ssr-columns="1"
        :column-width="300"
        :gap="16"
      >
        <template #default="{ item }">
          <div class="thumbnail-wrapper">
            <img :src="item.imageUrl" :alt="`${item.artworkTitle}`" />
            <div class="overlay">
              <div class="info">
                <router-link
                  :to="{ name: 'ArtworkDetail', params: { id: item.artworkId } }"
                >
                  <span style="color: white" class="artwork-title">{{
                    item.artworkTitle
                  }}</span>
                </router-link>
                <router-link
                  :to="{ name: 'Profile', params: { id: item.memberId } }"
                >
                  <span style="color: white" class="artwork-artist"
                    >By {{ item.memberNickname }}</span
                  >
                </router-link>
              </div>
              <div class="button-top-right">
                <!-- <like-button :liked="!valid" @click="handleLike"></like-button> -->
              </div>
            </div>
          </div>
        </template>
      </masonry-wall>
    </div>
    <!-- <div v-for="(item, i) in items" :key="i">
      <img :src="item.imageUrl" :alt="item.artworkTitle">
    </div> -->
    <Observer @intersect="intersected"/>
  </div>
</template>

<script>
import ArtworkAPI from "@/apis/artworkAPI";
import Observer from "@/components/main/child/Observer.vue";

export default {
  data: () => ({ page: 0, items: [] }),
  methods: {
    async intersected() {
      const res = await ArtworkAPI.getArtworkList(this.page);
      this.page++;
      const items = res.data;
      console.log(items);
      this.items = [...this.items, ...items];
    },
  },
  components: {
    Observer,
  },
};
</script>
<style scoped lang="scss">
footer {
  position: relative;
  width: 400px;

  #scroll-trigger {
    height: 100px;
  }
}

* {
  box-sizing: border-box;
}

.thumbnail-wrapper {
  position: relative;
  width: 300px;
  margin: 0 auto;
  transition: 0.5s ease-in-out;

  & img {
    max-width: 100%;
    height: auto;
    border: 1px solid $grey;
    box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.5);
  }

  &:hover {
    transform: translateY(-3px);
    & .button-top-right {
      visibility: visible;
    }
    & .info {
      color: white;
      visibility: visible;
      transform: translateY(0px);
    }
  }
}

.info {
  position: absolute;
  bottom: $font-small;
  left: 10px;
  visibility: hidden;
  z-index: 3;
  transition: 0.4s, ease-in-out;
}

.container {
  margin-top: 2rem;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 2;
  &:hover {
    background: rgba(0, 0, 0, 0.6);
  }
}

.button-top-right {
  position: absolute;
  top: 2px;
  right: 2px;
  z-index: 3;
  visibility: hidden;
}
</style>
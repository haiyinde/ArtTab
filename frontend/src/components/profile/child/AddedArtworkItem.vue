<template>
  <vue3-draggable-resizable
    class="artwork-box"
    :x="artwork.xloc"
    :y="artwork.yloc"
    :w="artwork.weight"
    :h="artwork.height"
    :active="active"
    :draggable="draggable"
    :resizable="resizable"
    :parent="true"
    @drag-end="updateLocation"
    @resize-end="updateSize"
  >
    <img :src="artwork.saveFolder" :alt="artwork.saveFileName" />
    <close-circle
      v-if="draggable"
      class="closeicon"
      @click="deleteArtwork"
    ></close-circle>
  </vue3-draggable-resizable>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Vue3DraggableResizable from "vue3-draggable-resizable";
import { CloseCircle } from "mdue";
import { mapActions } from "vuex";

const galleryStore = "galleryStore";

export default defineComponent({
  name: "AddedArtworkItem",
  components: {
    Vue3DraggableResizable,
    CloseCircle,
  },
  props: {
    artwork: {},
    index: Number,
    draggable: Boolean,
    resizable: Boolean,
  },
  data() {
    return {
      active: false,
    };
  },
  methods: {
    ...mapActions(galleryStore, [
      "deleteArtworkInList",
      "updateArtworkLoc",
      "updateArtworkSize",
    ]),
    // 위치 정보 저장하기
    updateLocation(val: any) {
      this.updateArtworkLoc({ x: val.x, y: val.y, index: this.index });
    },
    // 크기 정보 저장하기
    updateSize(val: any) {
      this.updateArtworkSize({
        x: val.x,
        y: val.y,
        w: val.w,
        h: val.h,
        index: this.index,
      });
    },
    deleteArtwork() {
      this.deleteArtworkInList(this.index);
    },
  },
});
</script>

<style lang="scss" scoped>
.artwork-box {
  img {
    width: 100%;
    height: 100%;
  }
  .closeicon {
    position: absolute;
    // top: 5;
    // right: 5;
  }
}
</style>

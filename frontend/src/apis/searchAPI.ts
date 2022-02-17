import api from "@/http-common";
// import ArtworkInfo from "@/types/ArtworkInfo";

class SearchAPI {
  SearchArtworks(title: string) {
    return api.get(`/searchTitle`, { params: { title: title } });
  }
}

export default new SearchAPI();

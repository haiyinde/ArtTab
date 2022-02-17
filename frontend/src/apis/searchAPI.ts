import api from "@/http-common";
// import ArtworkInfo from "@/types/ArtworkInfo";

class SearchAPI {
  SearchArtworks(id: number, title: string) {
    return api.get(`/searchTitle`, { params: { id: id, title: title } });
  }
}

export default new SearchAPI();

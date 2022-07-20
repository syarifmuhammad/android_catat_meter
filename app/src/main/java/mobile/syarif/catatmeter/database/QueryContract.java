package mobile.syarif.catatmeter.database;

import java.util.List;

import mobile.syarif.catatmeter.model.PelangganModel;
import mobile.syarif.catatmeter.model.PencatatanModel;

public class QueryContract {
    public interface PelangganQuery {
        void searchByIdOrName(String param, QueryResponse<List<PelangganModel>> response);
        void all(QueryResponse<List<PelangganModel>> response);
        void get(int id_pelanggan, QueryResponse<PelangganModel> response);
        void create(PelangganModel pelanggan, QueryResponse<Boolean> response);
        void update(PelangganModel pelanggan, QueryResponse<Boolean> response);
        void count(QueryResponse<Integer> integerQueryResponse);
        void countTercatat(QueryResponse<Integer> integerQueryResponse);
    }
    public interface PencatatanQuery {
//        void all(QueryResponse<List<PelangganModel>> response);
//        void get(Integer id_pelanggan, QueryResponse<PelangganModel> response);
        void getLastWhereIdPelanggan(int id_pelanggan, QueryResponse<PencatatanModel> response);
        void getCurrentPeriodeWhereIdPelanggan(int id_pelanggan, QueryResponse<PencatatanModel> response);
        void create(PencatatanModel pencatatan, QueryResponse<Boolean> response);
        void update(PencatatanModel pencatatan, QueryResponse<Boolean> response);
//
//        void count(QueryResponse<Integer> integerQueryResponse);
    }
}

import constants from '../core/constants';

class StockApi {

    constructor() {
        this.stock = [];
        this.stores = [];
    }

    findStoreAll() {
        // if stores list is already in cache, return it
        if (this.stores.length) {
            return Promise.resolve(this.stores);
        }
        else {
            return fetch(constants.get_stores_url).then(r => r.json())
                .then(data => {
                this.stores = data;
                return this.stores;
            })
            .catch(e => console.log(e));
        }
    }

    findStockByStore(storeId) {
        return fetch(constants.get_stock_url + "/" + storeId).then(r => r.json())
            .then(data => {
                this.stock = data;
                return this.stock;
            })
        .catch(e => console.log(e));
    }

}

export default new StockApi();

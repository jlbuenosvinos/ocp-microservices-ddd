import React from 'react';
import Layout from '../../components/Layout';
import AppListView from '../../components/ListView/AppListView';
import constants from '../../core/constants';

class StockPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
          apps: [],
          storeList: []
        };
      }

    componentWillMount() {
        this.getApps();
        this.getStoreList();
    }

    componentDidMount() {
        document.title = 'Stock Console Service | Stock Dashboard';
        document.body.style.backgroundColor = constants.bg_grey;
    }

    getApps() {
        const that = this;
        fetch(constants.get_apps_url).then(r => r.json())
        .then(data => {
            that.setState({ apps: data });
        })
        .catch(e => console.log(e));
    }


    getStoreList() {
        const that = this;
        fetch(constants.get_stores_url).then(r => r.json())
            .then(data => {
            that.setState({ storeList: data });
            })
            .catch(e => console.log(e));
    }


    render() {

        return (
            <Layout className="container-fluid container-pf-nav-pf-vertical" nav>

                <div className="row">
                    <div className="col-md-12">
                        <div className="page-header">
                            <h1>Stock Dashboard</h1>
                        </div>
                    </div>
                </div>

                <div className="row">
                    <div className="col-md-12">
                        <AppListView apps={this.state.apps}/>
                    </div>
                </div>

          </Layout>
        );
    }

}

export default StockPage;

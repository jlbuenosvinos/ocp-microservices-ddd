import React from 'react';
import Layout from '../../components/Layout';
import StockListView from '../../components/StockView/StockListView';
import constants from '../../core/constants';

class StockPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            stores: []
        };
    }

    componentWillMount() {
        this.getStores();
    }

    componentDidMount() {
        document.title = 'Stock Console Service | Stock Dashboard';
        document.body.style.backgroundColor = constants.bg_grey;
    }

    getStores() {
        const that = this;
        fetch(constants.get_stores_url).then(r => r.json())
            .then(data => {
            that.setState({ stores: data });
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
                        <StockListView stores={this.state.stores} />
                    </div>
                </div>

          </Layout>
        );
    }

}

export default StockPage;

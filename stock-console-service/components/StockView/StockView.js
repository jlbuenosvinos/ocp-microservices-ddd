import React from 'react';
import constants from '../../core/constants';
import {StockGraph} from 'react-chartjs-2';

class StockView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            stock: [],
            stockdata: { labels: ["January", "February", "March", "April", "May", "June", "July"],
                         datasets: [
                                        {
                                               label: "My First dataset",
                                               fillColor: "rgba(220,220,220,0.2)",
                                               strokeColor: "rgba(220,220,220,1)",
                                               pointColor: "rgba(220,220,220,1)",
                                               pointStrokeColor: "#fff",
                                               pointHighlightFill: "#fff",
                                               pointHighlightStroke: "rgba(220,220,220,1)",
                                               data: [65, 59, 80, 81, 56, 55, 40]
                               		    }
                         ]
                       }
        };
    }

    componentWillMount() {
        //this.findStockByStore();
        //this.getStores();
    }

    findStockByStore(storeId) {
        fetch("json/stock-store-" + storeId + ".json").then(r => r.json())
            .then(data => {
                this.setState({ stock: data });
            })
            .catch(e => console.log(e));
    }

    render() {

        const { storeId } = this.props;
        this.findStockByStore(storeId);
        let storeStock = this.state.stock;


        /*
        labels: ["January", "February", "March", "April", "May", "June", "July"],
                datasets: [{
                label: "My First dataset",
                backgroundColor: 'rgb(255, 99, 132)',
                borderColor: 'rgb(255, 99, 132)',
                data: [0, 10, 5, 2, 20, 30, 45],
                }]
        */

        var data = {
        	labels: ["January", "February", "March", "April", "May", "June", "July"],
        	datasets: [
        		    {
                        label: "My First dataset",
                        fillColor: "rgba(220,220,220,0.2)",
                        strokeColor: "rgba(220,220,220,1)",
                        pointColor: "rgba(220,220,220,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(220,220,220,1)",
                        data: [65, 59, 80, 81, 56, 55, 40]
        		    }
        	]
        };

        return (
            <div>
                Dashboard [{storeId}] goes here.
                <StockGraph data={data} height={500} width={700} />
            </div>
        );
    }

}

export default StockView;

import React from 'react';
import constants from '../../core/constants';

class StockView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            storeId: null,
            stock: []
        };
    }

    componentWillMount() {
        this.getStock();
    }

    getStock() {
        const that = this;
        fetch(constants.get_stock_url).then(r => r.json())
            .then(data => {
            that.setState({ stock: data });
            })
            .catch(e => console.log(e));
    }

    render() {

        const { storeId } = this.props;

        return (
            <div><h3>dashboard [{storeId}] goes here</h3></div>
        );
    }

}

export default StockView;

import React from 'react';
import StockView from './StockView'

class StockListView extends React.Component {

    static propTypes = {
        stores: React.PropTypes.array
    }

    componentDidMount() {
        this.bindExpand();
    }

    componentDidUpdate() {
        this.unbind();
        this.bindExpand();
    }

    componentWillUnmount() {
        this.unbind();
    }

    bindExpand() {
        // click the list-view heading then expand a row
        $('.list-group-item-header').click(function (event) {
            if (!$(event.target).is('button, a, input, .fa-ellipsis-v')) {
                $(this).find('.fa-angle-right')
                .toggleClass('fa-angle-down')
                .end()
                .parent()
                .toggleClass('list-view-pf-expand-active')
                .find('.list-group-item-container')
                .toggleClass('hidden');
            }
        });

        // click the close button, hide the expand row and remove the active status
        $('.list-group-item-container .close').on('click', function () {
            $(this).parent()
                .addClass('hidden')
                .parent()
                .removeClass('list-view-pf-expand-active')
                .find('.fa-angle-right')
                .removeClass('fa-angle-down');
        });
    }

    unbind() {
        $('.list-group-item-header').off('click');
        $('.list-group-item-container .close').off('click');
    }

    render() {

        const { stores } = this.props;

        return (

          <div className="list-group list-view-pf list-view-pf-view">

            {
            stores.map((store, i) =>

              <div className="list-group-item" key={i}>

                <div className="list-group-item-header">

                  <div className="list-view-pf-expand">
                    <span className="fa fa-angle-right"></span>
                  </div>

                  <div className="list-view-pf-main-info">
                    <div className="list-view-pf-left">
                      <span className="fa fa-area-chart list-view-pf-icon-sm"></span>
                    </div>
                    <div className="list-view-pf-body">
                      <div className="list-view-pf-description">
                        <div className="list-group-item-heading">
                          STORE [{store.store_name}] DASHBOARD
                        </div>
                      </div>
                    </div>
                  </div>

                </div>

                <div className="list-group-item-container container-fluid hidden">

                  <div className="close">
                    <span className="pficon pficon-close"></span>
                  </div>

                  <div className="row">
                    <div className="col-md-12">

                        <StockView storeId={store.store_id} />

                    </div>
                  </div>

                </div>

              </div>

            )}

          </div>
        );
    }

}

export default StockListView;

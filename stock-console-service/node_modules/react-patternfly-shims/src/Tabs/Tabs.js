import React, { PropTypes } from 'react';

/**
 * React <b>Tabs</b> Component for Patternfly Web Components
 *
 * @requires <b>pf-tabs</b> web component:
 * https://github.com/patternfly-webcomponents/patternfly-webcomponents/tree/master/src/pf-tabs
 *
 * @example {@lang xml}
 * <Tabs tabChanged={ this.tabChanged(e) }>
 *  <Tab tabTitle={"Tab1"} active={true}>
 *    <p>Tab1 content here</p>
 *  </Tab>
 *  <Tab tabTitle={"Tab2"}>
 *    <p>Tab2 content here</p>
 *  </Tab>
 * </Tabs>
 *
 */
class Tabs extends React.Component {

  static propTypes = {
    tabChanged: React.PropTypes.func
  };

  componentDidUpdate() {
    this.refs.pfTabs.addEventListener('tabChanged', (e) =>{
      if(this.props.tabChanged){
        this.props.tabChanged(e);
      }
    });
  }

  render() {
    return (
      <pf-tabs key="pf-tabs" ref="pfTabs">
        {this.props.children}
      </pf-tabs>
    )
  }
}

export default Tabs;
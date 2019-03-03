import React, { PropTypes } from 'react';

/**
 * React <b>UtilizationBarChart</b> Component for Patternfly Web Components
 *
 * @requires <b>pf-utilization-bar-chart</b> web component:
 * https://github.com/patternfly-webcomponents/patternfly-webcomponents/tree/master/src/pf-utilization-bar-chart
 *
 * @prop {string} chartTitle the chart title
 * @prop {string} layout 'inline' for inline layout
 * @prop {number} used the percentage used
 * @prop {number} total the percentage total
 * @prop {string} units the display units
 * @prop {number} thresholdWarning the warning threshold
 * @prop {number} thresholdError the error threshold
 * @prop {function} onThresholdSet callback function when threshold changes
 *
 *
 * @example {@lang xml}
 * <UtilizationBarChart  layout="default"
        chartTitle="RAM Usage" 
        used={this.state.used}
        total="100"
        units="MB"
        thresholdWarning="60"
        thresholdError="85"
        onThresholdSet={ this.onThresholdSet.bind(this)}>
 *  <span>Tooltip content</span>
 * </UtilizationBarChart>
 *
 */
class UtilizationBarChart extends React.Component {

  static propTypes = {
    chartTitle: React.PropTypes.string,
    layout: React.PropTypes.string,
    used: React.PropTypes.number,
    total: React.PropTypes.number,
    units: React.PropTypes.string,
    thresholdWarning: React.PropTypes.number,
    thresholdError: React.PropTypes.number,
    onThresholdSet: React.PropTypes.func
  };

componentDidUpdate() {
    this.refs.pfUtilizationBarChar.addEventListener('onThresholdSet', (e) =>{
      if(this.props.onThresholdSet){
        this.props.onThresholdSet(e);
      }
    });
  }

  render() {
    return (
      <pf-utilization-bar-chart key="pf-utilization-bar-chart" 
        chart-title={this.props.chartTitle}
        layout={this.props.layout}
        used={this.props.used}
        total={this.props.total}
        units={this.props.units}
        threshold-warning={this.props.thresholdWarning}
        threshold-error={this.props.thresholdError}
        ref="pfUtilizationBarChar">
      </pf-utilization-bar-chart>
    )
  }
}

export default UtilizationBarChart;
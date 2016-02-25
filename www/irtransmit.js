var irtransmit = {
    transmit: function(frequency, signal,successCallback, errorCallback){
	cordova.exec(successCallback,
	    errorCallback,
	    "IRTransmit",
	    "transmit",
	    [{
		"frequency": frequency,
		"patten": pattern
	    }]
	);
    },

    hasIrEmitter: function(successCallback, errorCallback) {
	console.log("hasIrEmitter: inside");
	cordova.exec(successCallback,
	    errorCallback,
	    "IRTransmit",
	    "hasIrEmitter",
	    [{
	    }]
	);
    }
}

module.exports = irtransmit;

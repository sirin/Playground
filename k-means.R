kcluster <- function(rows,k){
	numOfRow <- nrow(rows)
	numOfCol <- ncol(rows)
	#minVal is a vector that contains min values in columns
	minVal <- apply(rows,2,min)
	minVal <- as.vector(minVal)
	#maxVal is a vector that contains max values in columns
	maxVal <- apply(rows,2,max)
	maxVal <- as.vector(maxVal)
	#difference is a vector that contains difference between min and max values in columns
	difference <- as.vector(maxVal - minVal)
	ClusterList <- list()
	
	# Create k randomly placed centroids
	for (j in 1:k){
		#multiply each differences to element of uniform dist on (0,1)
		randed_diff <- as.vector(difference * runif(numOfCol,0,1))
		#added min values, so we have a random number between min and max values
		randed_diff <- as.vector(randed_diff + minVal)
		ClusterList[[j]] <- randed_diff
	}
	for (i in 1:100){
		#print(paste('Iteration', i))
		bestMatches <-rep(list(list()),k)
		
		# Find which centroid is the closest for each row
		for (j in 1:numOfRow){
			CurrentRow <- c(rows[j,])
			bestMatch <- 1
			for(i in 1:k){
				dist_1 <- sqrt(sum((ClusterList[[i]] - CurrentRow) ** 2))
				dist_2 <- sqrt(sum((ClusterList[[bestMatch]] -CurrentRow) ** 2))
				if (dist_1 < dist_2){
					bestMatch <- i
				}
			} 
			bestMatches[[c(bestMatch,j)]] <- j
		}
		
		# Move the centroids to the average of their members
		for(i in 1:k){
			avgs <- matrix(0,1,numOfCol)
			temp <-c()
			if (length(bestMatches[[i]]) > 0){
				for(i in bestMatches[[i]]){
					temp <- append(temp,i)
				}
				for(index in temp){
					avgs <-avgs+rows[index,]
				}
				avgs<- avgs/length(temp)
				ClusterList[[i]] <- avgs	
			}
		}		
	}
    
	return(bestMatches)	
}


FROM node:22 AS build

WORKDIR /app
COPY frontend-react/package*.json ./

RUN npm install
COPY ./frontend-react/ ./
RUN npm run build

EXPOSE 3000

CMD ["npm", "run", "start"]
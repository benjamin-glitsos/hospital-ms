import { Suspense, lazy } from "react";
import styled from "styled-components";
import { Router, Route, Switch } from "react-router";
import { createBrowserHistory } from "history";
import { QueryParamProvider } from "use-query-params";
import Analytics from "react-router-ga";
import Page from "%/presenters/PagePresenter";
import LoadingPage from "%/components/LoadingPage";

const ReadmePage = lazy(() => import("%/components/ReadmePage"));
const UsersPage = lazy(() => import("%/components/UsersPage"));
const NotFoundPage = lazy(() => import("%/components/NotFoundPage"));

export default () => (
    <Router history={createBrowserHistory()}>
        <ZIndexStyles>
            <Page>
                <Analytics
                    id={process.env.REACT_APP_PROJECT_GOOGLE_ANALYTICS_ID}
                >
                    <Suspense fallback={<LoadingPage />}>
                        <Switch>
                            <QueryParamProvider
                                ReactRouterRoute={Route}
                                exact
                                path="/"
                            >
                                <ReadmePage />
                            </QueryParamProvider>
                            <QueryParamProvider
                                ReactRouterRoute={Route}
                                path="/users"
                            >
                                <UsersPage />
                            </QueryParamProvider>
                            <QueryParamProvider
                                ReactRouterRoute={NotFoundPage}
                                path="*"
                            >
                                <NotFoundPage />
                            </QueryParamProvider>
                        </Switch>
                    </Suspense>
                </Analytics>
            </Page>
        </ZIndexStyles>
    </Router>
);

const ZIndexStyles = styled.div`
    div > div > div:nth-child(2) {
        z-index: 3;
    }
`;

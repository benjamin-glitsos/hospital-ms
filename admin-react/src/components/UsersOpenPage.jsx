import { Fragment, useState, useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm } from "react-hook-form";
import { buildYup } from "json-schema-to-yup";
import * as Yup from "yup";
import config from "%/config";
import axios from "axios";
import Form, { Field } from "@atlaskit/form";
import Textfield from "@atlaskit/textfield";
import Button from "@atlaskit/button";

export default () => {
    // TODO: First check that every feature will work
    // TODO: remove empty strings from the form data. You may need to use the Controller component from react-hook-form. And ensure that empty forms don't submit. And ensure that only changed fields get added to the object, the same fields as default don't get added.
    // TODO: add functionality to onSubmit that removes attributes that are blank strings or maybe also other blank properties e.g. []
    // TODO: use the atlaskit form fields

    const { username } = useParams();

    const defaultState = {
        schema: {},
        item: {}
    };

    const [state, setState] = useImmer(defaultState);

    const requestItem = () =>
        axios({
            method: "GET",
            url: config.serverUrl + `v1/users/${username}/`
        });

    const requestSchema = () =>
        axios({
            method: "GET",
            url: config.serverUrl + "v1/schemas/edit-users/"
        });

    const setItem = item =>
        setState(draft => {
            draft.item = item;
        });

    const setSchema = schema =>
        setState(draft => {
            draft.schema = schema;
        });

    const schemaAction = () => {
        (async () => {
            try {
                const schema = await requestSchema();
                setSchema(schema.data);
            } catch (error) {}
        })();
    };

    const openItemAction = () => {
        (async () => {
            try {
                const item = await requestItem();
                setItem(item.data.data);
            } catch (error) {}
        })();
    };

    useEffect(openItemAction, []);
    useEffect(schemaAction, []);

    const yupConfig = {
        abortEarly: false
    };

    const yupSchema =
        Object.keys(state.schema).length === 0
            ? Yup.object()
            : buildYup(state.schema);

    const formResolver = validationSchema =>
        useCallback(
            async data => {
                try {
                    const values = await validationSchema.validate(data, {
                        abortEarly: false
                    });

                    return {
                        values,
                        errors: {}
                    };
                } catch (errors) {
                    console.log(errors);
                    return {
                        values: {},
                        // TODO: map errors into the format: { keys: [error_messages] }
                        errors
                    };
                }
            },
            [validationSchema]
        );

    const onSubmit = data => console.log(data);

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm({
        resolver: formResolver(yupSchema)
    });

    console.log(state.item);

    const TextField = ({ name, title, register }) => {
        if (state.item[name]) {
            return (
                <Field label={title} name={name}>
                    {({ fieldProps }) => (
                        <Textfield
                            {...register(name)}
                            defaultValue={state.item[name]}
                            {...fieldProps}
                        />
                    )}
                </Field>
            );
        } else {
            return null;
        }
    };

    return (
        <Fragment>
            <h3>{state.item?.username}</h3>
            <Form onSubmit={handleSubmit(onSubmit)}>
                {({ formProps }) => (
                    <form {...formProps}>
                        <TextField
                            name="username"
                            title="Username"
                            register={register}
                        />
                        <TextField
                            name="email_address"
                            title="Email address"
                            register={register}
                        />
                        <TextField
                            name="first_name"
                            title="First name"
                            register={register}
                        />
                        <TextField
                            name="last_name"
                            title="Last name"
                            register={register}
                        />
                        <TextField
                            name="other_names"
                            title="Other names"
                            register={register}
                        />
                        <Button type="submit" appearance="primary">
                            Submit
                        </Button>
                    </form>
                )}
            </Form>
        </Fragment>
    );
};

// errors: errors.inner.reduce(
//     (allErrors, currentError) => ({
//         ...allErrors,
//         [currentError.path]: {
//             type: currentError.type ?? "validation",
//             message: currentError.message
//         }
//     }),
//     {}
// );

// <ul>
//     <li>{errors?.first_name?.message}</li>
// </ul>
// <ul>
//     <li>{errors?.username?.message}</li>
// </ul>
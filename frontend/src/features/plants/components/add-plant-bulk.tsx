import { useState } from 'react';

import { Button } from '@/components/catalyst/button';
import {
  Dialog,
  DialogActions,
  DialogBody,
  DialogTitle,
} from '@/components/catalyst/dialog';
import { Form, Input } from '@/components/ui/form';
import { useNotifications } from '@/components/ui/notifications';

import {
  addPlantBulkInputSchema,
  useAddPlantBulk,
} from '@/features/plants/api/add-plant-bulk';

export function AddPlantBulkModal({
  children,
}: {
  children?: React.ReactNode;
}) {
  const [isOpen, setIsOpen] = useState(false);
  const { addNotification } = useNotifications();

  const addPlantBulkMutation = useAddPlantBulk({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Bulk Upload Successful',
          message: 'All plants have been added.',
        });
        setIsOpen(false);
      },
      onError: () => {
        addNotification({
          type: 'error',
          title: 'Upload Failed',
          message: 'Please check your file and try again.',
        });
      },
    },
  });

  return (
    <>
      <Button onClick={() => setIsOpen(true)}>{children}</Button>
      <Dialog open={isOpen} onClose={setIsOpen} title="Add Plants (Bulk)">
        <DialogTitle>Upload a File</DialogTitle>
        <DialogBody>
          <Form
            id="add-plant-bulk"
            schema={addPlantBulkInputSchema}
            onSubmit={(values) => {
              addPlantBulkMutation.mutate({ data: values });
            }}
          >
            {({ register, formState }) => (
              <>
                <Input
                  type="file"
                  label="Upload File (.txt)"
                  accept=".txt"
                  registration={register('file', {
                    required: true,
                  })}
                  required
                  error={formState.errors['file']}
                />

                <DialogActions>
                  <Button outline onClick={() => setIsOpen(false)}>
                    Cancel
                  </Button>
                  <Button
                    type="submit"
                    disabled={addPlantBulkMutation.isPending}
                  >
                    Upload
                  </Button>
                </DialogActions>
              </>
            )}
          </Form>
        </DialogBody>
      </Dialog>
    </>
  );
}
